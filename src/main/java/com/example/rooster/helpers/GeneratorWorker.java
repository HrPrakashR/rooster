package com.example.rooster.helpers;

import com.example.rooster.employee.Employee;
import com.example.rooster.period.PeriodDTO;
import com.example.rooster.period.Purpose;
import com.example.rooster.team.Team;

import java.time.Duration;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

public class GeneratorWorker {

    public static List<PeriodDTO> generatePlan(List<PeriodDTO> predefinedPlan, List<Employee> employees, int year, int month, Team team){
        List<PeriodDTO> generatedPlan = new ArrayList<>(predefinedPlan);
        // iterate through days and employees
        employees.forEach(employee -> {
            AtomicInteger i = new AtomicInteger();
            i.incrementAndGet();
            DateWorker.getAllDaysOfMonth(year, month).forEach(day -> {
                // check if they have enough time to work at another day
                if (GeneratorWorker.CompulsoryWorkingHourDifference(
                        GeneratorWorker.getCompulsory(year, month, employee),
                        GeneratorWorker.getTotalWorkingHours(generatedPlan, employee, team),
                        employee
                ) > GeneratorWorker.getDailyWorkingHours(employee.getHoursPerWeek())
                        // check if there is no other period at this day
                        && predefinedPlan.stream()
                        .noneMatch(periodDTO -> periodDTO.getEmployee() == employee.getId() &&
                                periodDTO.getDateFrom().startsWith(String.format("%04d-%02d-%02d", year, month, i.get())))
                        // check the teams working times and the employees rest day
                        && GeneratorWorker.isWorkingDay(year, month, i.get(), team, employee)
                ) {
                    // TODO: zwischen hourTo und nächster hourFrom eines gleichen employees muessen team.getRestHours Stunden liegen
                    // TODO: beruecksichtige Requests

                    // initialize values
                    int hourFrom;
                    int hourTo;
                    int minuteFrom;
                    int minuteTo;
                    Purpose purpose = Purpose.WORKING_HOURS;

                    Calendar calendarFrom = GeneratorWorker.getFrom(team, year, month, i.get());
                    Calendar calendarTo = GeneratorWorker.getTo(team, year, month, i.get());
                    double workingHours = GeneratorWorker.getDailyWorkingHours(employee.getHoursPerWeek());
                    // early shift, late shift, middle shift
                    int randomNumber = (new Random()).nextInt(0, 100);
                    if (randomNumber < 35) {
                        hourFrom = calendarFrom.get(Calendar.HOUR_OF_DAY);
                        minuteFrom = calendarFrom.get(Calendar.MINUTE);
                        calendarFrom.add(Calendar.MINUTE, (int) Math.round(workingHours * 60));
                        calendarFrom.add(Calendar.MINUTE, (int) Math.round(team.getMinBreakTime() * 60));
                        hourTo = calendarFrom.get(Calendar.HOUR_OF_DAY);
                        minuteTo = calendarFrom.get(Calendar.MINUTE);
                    } else if (randomNumber < 80) {
                        hourTo = calendarTo.get(Calendar.HOUR_OF_DAY);
                        minuteTo = calendarTo.get(Calendar.MINUTE);
                        calendarTo.add(Calendar.MINUTE, ((int) Math.round(workingHours * 60)) * (-1));
                        calendarTo.add(Calendar.MINUTE, ((int) Math.round(team.getMinBreakTime() * 60)) * (-1));
                        hourFrom = calendarTo.get(Calendar.HOUR_OF_DAY);
                        minuteFrom = calendarTo.get(Calendar.MINUTE);
                    } else {
                        double differenceHours = DateWorker.calculateHours(DateWorker.convertDateToDateString(calendarFrom.getTime()), DateWorker.convertDateToDateString(calendarTo.getTime()));
                        if (differenceHours > GeneratorWorker.getDailyWorkingHours(employee.getHoursPerWeek())) {
                            calendarFrom.add(Calendar.MINUTE, (int) Math.round(differenceHours * 60) / 4);
                            calendarTo.add(Calendar.MINUTE, ((int) Math.round(differenceHours * 60) / 4) * (-1));
                        }
                        hourFrom = calendarFrom.get(Calendar.HOUR_OF_DAY);
                        hourTo = calendarTo.get(Calendar.HOUR_OF_DAY);
                        minuteFrom = calendarFrom.get(Calendar.MINUTE);
                        minuteTo = calendarTo.get(Calendar.MINUTE);
                    }

                    // if working time is not in the
                    if (calendarTo.before(calendarFrom)) {
                        hourTo = calendarFrom.get(Calendar.HOUR_OF_DAY);
                        minuteTo = calendarFrom.get(Calendar.MINUTE);
                    }
                    if (calendarFrom.after(calendarTo)) {
                        hourTo = calendarTo.get(Calendar.HOUR_OF_DAY);
                        minuteTo = calendarTo.get(Calendar.MINUTE);
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

                    // calculate with breakTime
                    createdPeriodDTO.setDateTo(GeneratorWorker.addHoursToDateString(createdPeriodDTO.getDateTo(), team.getMinBreakTime()));

                    // a little randomizing the creation
                    if ((new Random()).nextInt(0, 7) < GeneratorWorker.getDailyWorkingHours(employee.getHoursPerWeek())) {
                        generatedPlan.add(createdPeriodDTO);
                    }

                    // !!!!Next TODO: ueberpruefe am Ende, ob alle Zeiten abgedeckt sind. Ansonsten fuelle diese Daten
                    if(DateWorker.getDateObjectYMD(year, month, i.get()).after(DateWorker.getDateObjectYMD(year, month, DateWorker.getAllDaysOfMonth(year, month).size()-1))){
                        System.out.println("we need more times");
                    }
                }
                i.incrementAndGet();
            });
        });
        return generatedPlan;
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
        Calendar calendar = DateWorker.getCalendarObject(DateWorker.convertDateStringToDate(dateString));
        calendar.add(Calendar.MINUTE, (int) Math.round(timeToAdd * 60));
        return DateWorker.convertDateToDateString(calendar.getTime());
    }

    public static boolean isWorkingDay(int year, int month, int day, Team team, Employee employee) {
        // check if we can lay the team.getRestDays on the teamWorkingDays
        Calendar calendar = DateWorker.getCalendarObject(DateWorker.getDateObjectYMD(year, month, day));
        List<Integer> workingDays = new ArrayList<>();
        List<Integer> removeDays = new ArrayList<>();
        for (int i = 1; i <= calendar.getActualMaximum(Calendar.DAY_OF_MONTH); i++) {
            calendar.set(Calendar.DAY_OF_MONTH, i);
            if (!DateWorker.checkIfTeamWorksAtDay(team, calendar.get(Calendar.DAY_OF_WEEK))) {
                if ((new Random()).nextInt(0, 100) < 50) {
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
        Calendar calendar = DateWorker.getCalendarObject(DateWorker.getDateObjectYMD(year, month, day));
        return switch (calendar.get(Calendar.DAY_OF_WEEK)) {
            case 2 -> DateWorker.getCalendarObject(team.getMondayFrom());
            case 3 -> DateWorker.getCalendarObject(team.getTuesdayFrom());
            case 4 -> DateWorker.getCalendarObject(team.getWednesdayFrom());
            case 5 -> DateWorker.getCalendarObject(team.getThursdayFrom());
            case 6 -> DateWorker.getCalendarObject(team.getFridayFrom());
            case 7 -> DateWorker.getCalendarObject(team.getSaturdayFrom());
            default -> DateWorker.getCalendarObject(team.getSundayFrom());
        };
    }

    public static Calendar getTo(Team team, int year, int month, int day) {
        Calendar calendar = DateWorker.getCalendarObject(DateWorker.getDateObjectYMD(year, month, day));
        return switch (calendar.get(Calendar.DAY_OF_WEEK)) {
            case 2 -> DateWorker.getCalendarObject(team.getMondayTo());
            case 3 -> DateWorker.getCalendarObject(team.getTuesdayTo());
            case 4 -> DateWorker.getCalendarObject(team.getWednesdayTo());
            case 5 -> DateWorker.getCalendarObject(team.getThursdayTo());
            case 6 -> DateWorker.getCalendarObject(team.getFridayTo());
            case 7 -> DateWorker.getCalendarObject(team.getSaturdayTo());
            default -> DateWorker.getCalendarObject(team.getSundayTo());
        };
    }
}