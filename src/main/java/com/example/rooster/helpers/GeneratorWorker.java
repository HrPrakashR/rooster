package com.example.rooster.helpers;

import com.example.rooster.employee.Employee;
import com.example.rooster.period.PeriodDTO;
import com.example.rooster.period.Purpose;
import com.example.rooster.team.Team;

import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.stream.Stream;

public class GeneratorWorker {

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