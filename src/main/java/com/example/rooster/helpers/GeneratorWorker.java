package com.example.rooster.helpers;

import com.example.rooster.employee.Employee;
import com.example.rooster.period.PeriodDTO;
import com.example.rooster.period.Purpose;
import com.example.rooster.team.Team;

import java.time.Duration;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Utilities to generate the roster
 */
public class GeneratorWorker {

    /**
     * Generates a new roster
     *
     * @param predefinedPlan List<PeriodDTO> List with predefined values
     * @param requestList    List<PeriodDTO> List with requests from employees
     * @param employees      List<Employee> List with all employees of this roster
     * @param year           Year
     * @param month          Month
     * @param team           Team as object
     * @return New roster of this month (List with PeriodDTO's)
     */
    public static List<PeriodDTO> generatePlan(List<PeriodDTO> predefinedPlan, List<PeriodDTO> requestList, List<Employee> employees, int year, int month, Team team) {
        // Initialize important variables
        List<PeriodDTO> generatedPlan = new ArrayList<>(predefinedPlan);
        AtomicBoolean freeDaySwitch = new AtomicBoolean((new Random()).nextInt(0, 2) == 0);
        AtomicBoolean twoEmployeesSwitch = new AtomicBoolean((new Random()).nextInt(0, 2) == 0);
        AtomicBoolean beginWithEarly = new AtomicBoolean((new Random()).nextInt(0, 2) == 0);
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
                if (CompulsoryWorkingHourDifference(
                        getCompulsory(year, month, employee),
                        getTotalWorkingHours(generatedPlan, employee, team),
                        employee
                ) > getDailyWorkingHours(employee.getHoursPerWeek())
                        // check if there is no other period at this day
                        && predefinedPlan.stream()
                        .noneMatch(periodDTO -> periodDTO.getEmployee() == employee.getId()
                                && DateWorker.checkIfPeriodDTOContainsDate(periodDTO, i.get(), month, year))
                        // check the teams working times and the employees rest day
                        && isWorkingDay(year, month, i.get(), team, freeDaySwitch.get())
                        && weeklyWorkingTime.getAndAdd((int) getTotalWorkingHours(
                        generatedPlan.stream().filter(periodDTO -> periodDTO.getEmployee() == employee.getId()
                                && DateWorker.checkIfPeriodDTOContainsDate(periodDTO,
                                DateWorker.getCalendarObject(0, 0, 0, i.get(), month, year).getFirstDayOfWeek(), month, year)).toList(),
                        employee, team)) <= employee.getHoursPerWeek()
                        && !freeTimeRequest.get()
                ) {
                    // initialize values
                    int hourFrom = 0;
                    int hourTo;
                    int minuteFrom = 0;
                    int minuteTo;
                    Purpose purpose = Purpose.WORKING_HOURS;
                    Calendar calendarFrom = getFrom(team, year, month, i.get());
                    Calendar calendarTo = getTo(team, year, month, i.get());

                    double differenceHours = DateWorker.calculateHours(
                            DateWorker.convertDateToDateString(calendarFrom.getTime()),
                            DateWorker.convertDateToDateString(calendarTo.getTime()));
                    double workingHours = getDailyWorkingHours(employee.getHoursPerWeek()) + team.getMinBreakTime();

                    // switch shifts
                    if (employees.size() == 1 || differenceHours <= workingHours) {
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
                            hourFrom = calendarFrom.get(Calendar.HOUR_OF_DAY);
                            minuteFrom = calendarFrom.get(Calendar.MINUTE);
                            calendarFrom.add(Calendar.MINUTE, (int) Math.round(differenceHours * 60) / 4);
                            calendarFrom.add(Calendar.MINUTE, (int) Math.round(workingHours * 60));
                            hourTo = calendarFrom.get(Calendar.HOUR_OF_DAY);
                            minuteTo = calendarFrom.get(Calendar.MINUTE);
                        }
                    }

                    // if working time is not in the
                    if (hourFrom < getFrom(team, year, month, i.get()).get(Calendar.HOUR_OF_DAY)) {
                        hourFrom = getFrom(team, year, month, i.get()).get(Calendar.HOUR_OF_DAY);
                        minuteFrom = getFrom(team, year, month, i.get()).get(Calendar.MINUTE);
                    }
                    if (hourTo > getTo(team, year, month, i.get()).get(Calendar.HOUR_OF_DAY)) {
                        hourTo = getTo(team, year, month, i.get()).get(Calendar.HOUR_OF_DAY);
                        minuteTo = getTo(team, year, month, i.get()).get(Calendar.MINUTE);
                    }

                    // add working times
                    PeriodDTO createdPeriodDTO = createPeriodDTO(
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

    /**
     * Get dily working hours from weekly working hours
     *
     * @param weeklyWorkingTime weekly working hours
     * @return daily working hours as Double
     */
    public static Double getDailyWorkingHours(Double weeklyWorkingTime) {
        return weeklyWorkingTime / 5;
    }

    /**
     * creates a PeriodDTO
     *
     * @param day           Day
     * @param month         Month
     * @param year          Year
     * @param hourFrom      Hour from
     * @param minuteFrom    Minute from
     * @param hourTo        Hour to
     * @param minuteTo      Minute to
     * @param employeeId    Employee Id
     * @param purposeString Purpose as String
     * @return PeriodDTO
     */
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

    /**
     * Calculates the difference between the compulsory and real working hours
     *
     * @param compulsory   Monthly compulsory working hours
     * @param workingHours Real monthly working hours
     * @param employee     Employee as object
     * @return Double with Difference
     */
    public static Double CompulsoryWorkingHourDifference(double compulsory, double workingHours, Employee employee) {
        return compulsory - workingHours - employee.getBalanceHours();
    }

    /**
     * Calculates the total working hours of an employee
     *
     * @param workingTimes List with working times as PeriodDTO's
     * @param employee     Employee as Object
     * @param team         Team as Object
     * @return Total working hours as Double
     */
    public static double getTotalWorkingHours(List<PeriodDTO> workingTimes, Employee employee, Team team) {
        double total = workingTimes.stream().filter(periodDTO ->
                        periodDTO.getEmployee() == employee.getId() &&
                                periodDTO.getPurpose().equals(Purpose.WORKING_HOURS.name())
                )
                .mapToDouble(periodDTO ->
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
                .mapToDouble(periodDTO -> getDailyWorkingHours(employee.getHoursPerWeek())).sum();

        return total;
    }

    /**
     * Calculate the compulsory working time of an employee
     *
     * @param year     Year
     * @param month    Month
     * @param employee Employee as Object
     * @return Compulsory as double
     */
    public static double getCompulsory(int year, int month, Employee employee) {
        return DateWorker.countAllWeekdaysOfMonth(year, month) *
                getDailyWorkingHours(employee.getHoursPerWeek());
    }

    /**
     * Checks if an Employee shout get another workday on this date
     *
     * @param year          Year
     * @param month         Month
     * @param day           Day
     * @param team          Team as Object
     * @param freeDaySwitch Boolean if employee has a free day request
     * @return Boolean if an Employee should work
     */
    public static boolean isWorkingDay(int year, int month, int day, Team team, boolean freeDaySwitch) {
        Calendar calendar = DateWorker.getCalendarObject(0, 0, 0, day, year, month);
        List<Integer> workingDays = new ArrayList<>();
        List<Integer> removeDays = new ArrayList<>();
        for (int i = 1; i <= calendar.getActualMaximum(Calendar.DAY_OF_MONTH); i++) {
            calendar.set(Calendar.DAY_OF_MONTH, i);
            if (!checkIfTeamWorksAtDay(team, calendar.get(Calendar.DAY_OF_WEEK))) {
                if (freeDaySwitch) {
                    IntStream.range(i, (i + team.getRestDays())).forEachOrdered(removeDays::add);
                } else {
                    int finalI = i;
                    IntStream.iterate(i, n -> n > (finalI - team.getRestDays()), n -> n - 1).forEachOrdered(removeDays::add);
                }

            }
            workingDays.add(i);
        }
        workingDays.removeAll(removeDays);
        return workingDays.contains(day);
    }

    /**
     * Returns the beginning working time of a team from a specific date
     *
     * @param team  Team as object
     * @param year  Year
     * @param month Month
     * @param day   Day
     * @return Calendar object with the beginning working time of a team.
     */
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

    /**
     * Returns the end of working time of a team from a specific date
     *
     * @param team  Team as object
     * @param year  Year
     * @param month Month
     * @param day   Day
     * @return Calendar object with the end working time of a team.
     */
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

    /**
     * Returns the working time of a PeriodDTO List
     *
     * @param workingHours      List with PeriodDTO including working times
     * @param dailyWorkingHours Double with daily working hours
     * @return Double with working time
     */
    public static Double getWorkingTime(List<PeriodDTO> workingHours, Double dailyWorkingHours) {

        if (workingHours.isEmpty()) {
            return 0.0;
        }

        double result = workingHours
                .stream()
                .filter(period ->
                        Objects.equals(period.getPurpose(), Purpose.CONFIRMED_VACATION.name()) ||
                                Objects.equals(period.getPurpose(), Purpose.SICK_LEAVE.name())
                ).mapToDouble(period -> dailyWorkingHours)
                .sum();

        result += workingHours
                .stream()
                .filter(period -> Objects.equals(period.getPurpose(), Purpose.WORKING_HOURS.name()))
                .mapToDouble(period -> DateWorker.calculateHours(period.getDateFrom(), period.getDateTo()))
                .sum();

        return result;
    }

    /**
     * Checks if the team works at a specific weekday
     *
     * @param team Team
     * @param day  Weekday (1 = Sunday, 2 = Monday, ...)
     * @return Boolean
     */
    public static boolean checkIfTeamWorksAtDay(Team team, int day) {
        switch (day) {
            case 1 -> {
                return team.getSundayFrom() != null;
            }
            case 2 -> {
                return team.getMondayTo() != null;
            }
            case 3 -> {
                return team.getTuesdayFrom() != null;
            }
            case 4 -> {
                return team.getWednesdayFrom() != null;
            }
            case 5 -> {
                return team.getThursdayFrom() != null;
            }
            case 6 -> {
                return team.getFridayFrom() != null;
            }
            case 7 -> {
                return team.getSaturdayFrom() != null;
            }
            default -> {
                return false;
            }
        }
    }
}