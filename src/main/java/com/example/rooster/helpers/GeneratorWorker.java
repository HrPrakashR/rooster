package com.example.rooster.helpers;

import com.example.rooster.employee.Employee;
import com.example.rooster.period.PeriodDTO;
import com.example.rooster.period.Purpose;
import com.example.rooster.team.Team;

import java.time.Duration;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

public class GeneratorWorker {

    public static List<PeriodDTO> generatePlan(List<PeriodDTO> predefinedPlan, List<PeriodDTO> requestList, List<Employee> employees, int year, int month, Team team) {
        // Initialize important variables
        List<PeriodDTO> generatedPlan = new ArrayList<>(predefinedPlan);
        AtomicBoolean freeDaySwitch = new AtomicBoolean((new Random()).nextInt(0, 2) == 0);
        AtomicBoolean twoEmployeesSwitch = new AtomicBoolean((new Random()).nextInt(0, 2) != 0);
        AtomicBoolean beginWithEarly = new AtomicBoolean((new Random()).nextInt(0, 2) != 0);
        AtomicInteger switchPeriods = new AtomicInteger((new Random()).nextInt(0, 3));
        AtomicInteger weeklyWorkingTime = new AtomicInteger(0);
        boolean[] earlyCheck = new boolean[DateWorker.getAllDaysOfMonth(year, month).size()];
        boolean[] lateCheck = new boolean[DateWorker.getAllDaysOfMonth(year, month).size()];

        // iterate through each employee of a team
        employees.forEach(employee -> {

            // Initialize and reset important variables
            weeklyWorkingTime.set(0);
            freeDaySwitch.set(!freeDaySwitch.get());
            twoEmployeesSwitch.set(!twoEmployeesSwitch.get());
            AtomicInteger i = new AtomicInteger();
            if (switchPeriods.getAndIncrement() == 2) {
                switchPeriods.set(0);
            }
            i.incrementAndGet();

            DateWorker.getAllDaysOfMonth(year, month).forEach(day -> {
                AtomicBoolean freeTimeRequest = new AtomicBoolean(false);

                if (DateWorker.getCalendarObject(0, 0, 0, i.get(), month, year).get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
                    weeklyWorkingTime.set(0);
                }

                Optional<PeriodDTO> actualRequest = requestList.stream().filter(periodDTO ->
                        periodDTO.getEmployee() == employee.getId()
                                && DateWorker.checkIfPeriodDTOContainsDate(periodDTO, i.get(), month, year))
                        .findFirst();

                if (actualRequest.isPresent()) {
                    switch (actualRequest.get().getPurpose()) {
                        case "FREE_TIME_REQUEST" -> freeTimeRequest.set(true);
                        case "VACATION_REQUEST" -> {
                            actualRequest.get().setPurpose("CONFIRMED_VACATION");
                            generatedPlan.add(actualRequest.get());
                            freeTimeRequest.set(true);
                        }
                        case "WORKING_HOUR_REQUEST" -> {
                            actualRequest.get().setPurpose("WORKING_HOURS");
                            generatedPlan.add(actualRequest.get());
                        }
                    }
                }

                // check if they have enough time to work at another day
                if (GeneratorWorker.CompulsoryWorkingHourDifference(
                        GeneratorWorker.getCompulsory(year, month, employee),
                        GeneratorWorker.getTotalWorkingHours(generatedPlan, employee, team),
                        employee
                ) > GeneratorWorker.getDailyWorkingHours(employee.getHoursPerWeek())
                        // check if there is no other period at this day
                        && predefinedPlan.stream()
                        .noneMatch(periodDTO -> periodDTO.getEmployee() == employee.getId()
                                        && DateWorker.checkIfPeriodDTOContainsDate(periodDTO, i.get(), month, year))
                        // check the teams working times and the employees rest day
                        && GeneratorWorker.isWorkingDay(year, month, i.get(), team, freeDaySwitch.get())
                        && weeklyWorkingTime.getAndAdd((int) GeneratorWorker.getTotalWorkingHours(
                        generatedPlan.stream().filter(periodDTO -> periodDTO.getEmployee() == employee.getId()
                                && periodDTO.getDateFrom().startsWith(String.format("%04d-%02d-%02d", year, month,
                                DateWorker.convertDateToCalendarObject(DateWorker.getDateObjectYMD(year, month, i.get())).getFirstDayOfWeek()))).toList(),
                        employee, team)) <= employee.getHoursPerWeek()
                        && !freeTimeRequest.get()
                ) {
                    // TODO: beruecksichtige Requests

                    // initialize values
                    int hourFrom;
                    int hourTo;
                    int minuteFrom;
                    int minuteTo;
                    Purpose purpose = Purpose.WORKING_HOURS;

                    Calendar calendarFrom = GeneratorWorker.getFrom(team, year, month, i.get());
                    Calendar calendarTo = GeneratorWorker.getTo(team, year, month, i.get());
                    double differenceHours = DateWorker.calculateHours(DateWorker.convertDateToDateString(calendarFrom.getTime()), DateWorker.convertDateToDateString(calendarTo.getTime()));
                    double workingHours = GeneratorWorker.getDailyWorkingHours(employee.getHoursPerWeek()) + team.getMinBreakTime();

                    // switch shifts
                    if (employees.size() == 1 || differenceHours <= workingHours) {
                        hourFrom = calendarFrom.get(Calendar.HOUR_OF_DAY);
                        minuteFrom = calendarFrom.get(Calendar.MINUTE);
                        hourTo = calendarTo.get(Calendar.HOUR_OF_DAY);
                        minuteTo = calendarTo.get(Calendar.MINUTE);
                    } else {
                        if (((!earlyCheck[i.get() - 1] && switchPeriods.get() == 0 || switchPeriods.get() == 1) && beginWithEarly.get()) || (switchPeriods.get() == 0 && employees.size() > 2) || (twoEmployeesSwitch.get() && employees.size() == 2)) {
                            earlyCheck[i.get() - 1] = true;
                            hourFrom = calendarFrom.get(Calendar.HOUR_OF_DAY);
                            minuteFrom = calendarFrom.get(Calendar.MINUTE);
                            calendarFrom.add(Calendar.MINUTE, (int) Math.round(workingHours * 60));
                            hourTo = calendarFrom.get(Calendar.HOUR_OF_DAY);
                            minuteTo = calendarFrom.get(Calendar.MINUTE);
                        } else if ((!lateCheck[i.get() - 1] && switchPeriods.get() == 2 || switchPeriods.get() == 1) || (switchPeriods.get() == 2 && employees.size() > 2) || (!twoEmployeesSwitch.get() && employees.size() == 2)) {
                            lateCheck[i.get() - 1] = true;
                            hourTo = calendarTo.get(Calendar.HOUR_OF_DAY);
                            minuteTo = calendarTo.get(Calendar.MINUTE);
                            calendarTo.add(Calendar.MINUTE, ((int) Math.round(workingHours * 60)) * (-1));
                            hourFrom = calendarTo.get(Calendar.HOUR_OF_DAY);
                            minuteFrom = calendarTo.get(Calendar.MINUTE);
                        } else {
                            calendarFrom.add(Calendar.MINUTE, (int) Math.round(differenceHours * 60) / 4);
                            hourFrom = calendarFrom.get(Calendar.HOUR_OF_DAY);
                            minuteFrom = calendarFrom.get(Calendar.MINUTE);
                            calendarFrom.add(Calendar.MINUTE, (int) Math.round(workingHours * 60));
                            hourTo = calendarFrom.get(Calendar.HOUR_OF_DAY);
                            minuteTo = calendarFrom.get(Calendar.MINUTE);
                        }
                    }

                    // if working time is not in the
                    if (hourFrom < GeneratorWorker.getFrom(team, year, month, i.get()).get(Calendar.HOUR_OF_DAY)) {
                        hourFrom = GeneratorWorker.getFrom(team, year, month, i.get()).get(Calendar.HOUR_OF_DAY);
                        minuteFrom = GeneratorWorker.getFrom(team, year, month, i.get()).get(Calendar.MINUTE);
                    }
                    if (hourTo > GeneratorWorker.getTo(team, year, month, i.get()).get(Calendar.HOUR_OF_DAY)) {
                        hourTo = GeneratorWorker.getTo(team, year, month, i.get()).get(Calendar.HOUR_OF_DAY);
                        minuteTo = GeneratorWorker.getTo(team, year, month, i.get()).get(Calendar.MINUTE);
                    }

                    // add working times
                    PeriodDTO createdPeriodDTO = GeneratorWorker.createPeriodDTO(
                            i.get(),
                            month,
                            year,
                            hourFrom,
                            minuteFrom,
                            hourTo,
                            minuteTo,
                            employee.getId(),
                            purpose.name());

                    generatedPlan.add(createdPeriodDTO);

                }
                i.incrementAndGet();
            });
        });

        return generatedPlan;
    }

    private static boolean missingWorkingTime(Team team, List<PeriodDTO> generatedPlan) {
        List<Integer> daysNeeded = new ArrayList<>();
        generatedPlan.forEach(periodDTO -> {
            Calendar calendarFrom = DateWorker.convertDateStringToCalendar(periodDTO.getDateFrom());
            Calendar calendarTo = DateWorker.convertDateStringToCalendar(periodDTO.getDateTo());

            if (GeneratorWorker.getFrom(team,
                    calendarFrom.get(Calendar.YEAR),
                    calendarFrom.get(Calendar.MONTH),
                    calendarFrom.get(Calendar.DAY_OF_MONTH)
            ).after(calendarFrom.getTime())) {
                daysNeeded.add(calendarFrom.get(Calendar.DAY_OF_MONTH));
            }

            if (GeneratorWorker.getTo(team,
                    calendarTo.get(Calendar.YEAR),
                    calendarTo.get(Calendar.MONTH),
                    calendarTo.get(Calendar.DAY_OF_MONTH)
            ).before(calendarTo.getTime())) {
                daysNeeded.add(calendarTo.get(Calendar.DAY_OF_MONTH));
            }

        });
        return !daysNeeded.isEmpty();
    }

    public static Double getDailyWorkingHours(Double weeklyWorkingTime) {
        return weeklyWorkingTime / 5;
    }

    public static List<PeriodDTO> filterByEmployee(List<PeriodDTO> periodDTOList, long employeeId) {
        return periodDTOList.stream().filter(periodDTO -> periodDTO.getEmployee() == employeeId).toList();
    }

    public static PeriodDTO createPeriodDTO(int day, int month, int year, int hourFrom, int minuteFrom, int hourTo, int minuteTo, long employeeId, String purposeString) {
        Date from = DateWorker.getDateObject(0, minuteFrom, hourFrom, day, month, year);
        Date to = DateWorker.getDateObject(0, minuteTo, hourTo, day, month, year);

        PeriodDTO newPeriodDTO = new PeriodDTO();
        newPeriodDTO.setDateFrom(DateWorker.convertDateToDateString(from));
        newPeriodDTO.setDateTo(DateWorker.convertDateToDateString(to));
        newPeriodDTO.setEmployee(employeeId);
        newPeriodDTO.setPurpose(purposeString);

        return newPeriodDTO;
    }

    public static Double CompulsoryWorkingHourDifference(double compulsory, double workingHours, Employee employee) {
        return compulsory - workingHours - employee.getBalanceHours();
    }

    public static double getTotalWorkingHours(List<PeriodDTO> workingTimes, Employee employee, Team team) {
        double total = workingTimes.stream().filter(periodDTO ->
                        periodDTO.getEmployee() == employee.getId() &&
                                periodDTO.getPurpose().equals(Purpose.WORKING_HOURS.name())
                )
                .mapToDouble(periodDTO ->
                        // Stunden zaehlen
                        Duration.between(
                                DateWorker.convertDateStringToDate(periodDTO.getDateFrom()).toInstant(),
                                DateWorker.convertDateStringToDate(periodDTO.getDateTo()).toInstant()
                        ).toHours() - team.getMinBreakTime()
                ).sum();

        total += workingTimes.stream().filter(periodDTO ->
                        periodDTO.getEmployee() == employee.getId() &&
                                Stream.of(Purpose.CONFIRMED_VACATION.name(), Purpose.SICK_LEAVE.name())
                                        .anyMatch(purpose -> periodDTO.getPurpose().equals(purpose))
                )
                .mapToDouble(periodDTO -> GeneratorWorker.getDailyWorkingHours(employee.getHoursPerWeek())).sum();

        return total;
    }

    public static double getCompulsory(int year, int month, Employee employee) {
        return DateWorker.countAllWeekdaysOfMonth(year, month) *
                GeneratorWorker.getDailyWorkingHours(employee.getHoursPerWeek());
    }

    public static String addHoursToDateString(String dateString, double timeToAdd) {
        Calendar calendar = DateWorker.convertDateToCalendarObject(DateWorker.convertDateStringToDate(dateString));
        calendar.add(Calendar.MINUTE, (int) Math.round(timeToAdd * 60));
        return DateWorker.convertDateToDateString(calendar.getTime());
    }

    public static boolean isWorkingDay(int year, int month, int day, Team team, boolean freeDaySwitch) {
        // check if we can lay the team.getRestDays on the teamWorkingDays
        Calendar calendar = DateWorker.getCalendarObject(0, 0, 0, day, year, month);
        List<Integer> workingDays = new ArrayList<>();
        List<Integer> removeDays = new ArrayList<>();
        for (int i = 1; i <= calendar.getActualMaximum(Calendar.DAY_OF_MONTH); i++) {
            calendar.set(Calendar.DAY_OF_MONTH, i);
            if (!DateWorker.checkIfTeamWorksAtDay(team, calendar.get(Calendar.DAY_OF_WEEK))) {
                if (freeDaySwitch) {
                    for (int n = i; n < (i + team.getRestDays()); n++) {
                        removeDays.add(n);
                    }
                } else {
                    for (int n = i; n > (i - team.getRestDays()); n--) {
                        removeDays.add(n);
                    }
                }

            }
            workingDays.add(i);

        }
        workingDays.removeAll(removeDays);
        return workingDays.contains(day);
    }

    public static Calendar getFrom(Team team, int year, int month, int day) {
        Calendar calendar = DateWorker.getCalendarObject(0, 0, 0, day, year, month);
        return switch (calendar.get(Calendar.DAY_OF_WEEK)) {
            case 2 -> DateWorker.convertDateToCalendarObject(team.getMondayFrom());
            case 3 -> DateWorker.convertDateToCalendarObject(team.getTuesdayFrom());
            case 4 -> DateWorker.convertDateToCalendarObject(team.getWednesdayFrom());
            case 5 -> DateWorker.convertDateToCalendarObject(team.getThursdayFrom());
            case 6 -> DateWorker.convertDateToCalendarObject(team.getFridayFrom());
            case 7 -> DateWorker.convertDateToCalendarObject(team.getSaturdayFrom());
            default -> DateWorker.convertDateToCalendarObject(team.getSundayFrom());
        };
    }

    public static Calendar getTo(Team team, int year, int month, int day) {
        Calendar calendar = DateWorker.getCalendarObject(0, 0, 0, day, year, month);
        return switch (calendar.get(Calendar.DAY_OF_WEEK)) {
            case 2 -> DateWorker.convertDateToCalendarObject(team.getMondayTo());
            case 3 -> DateWorker.convertDateToCalendarObject(team.getTuesdayTo());
            case 4 -> DateWorker.convertDateToCalendarObject(team.getWednesdayTo());
            case 5 -> DateWorker.convertDateToCalendarObject(team.getThursdayTo());
            case 6 -> DateWorker.convertDateToCalendarObject(team.getFridayTo());
            case 7 -> DateWorker.convertDateToCalendarObject(team.getSaturdayTo());
            default -> DateWorker.convertDateToCalendarObject(team.getSundayTo());
        };
    }

    public static Double getWorkingTime(List<PeriodDTO> workingHours, Double dailyWorkingHours) {

        if (workingHours.isEmpty()) {
            return 0.0;
        }

        double wh = workingHours
                .stream()
                .filter(period ->
                        Objects.equals(period.getPurpose(), Purpose.CONFIRMED_VACATION.name()) ||
                                Objects.equals(period.getPurpose(), Purpose.SICK_LEAVE.name())
                ).mapToDouble(period -> dailyWorkingHours)
                .sum();

        wh += workingHours
                .stream()
                .filter(period -> Objects.equals(period.getPurpose(), Purpose.WORKING_HOURS.name()))
                .mapToDouble(period -> DateWorker.calculateHours(period.getDateFrom(), period.getDateTo()))
                .sum();

        return wh;
    }
}