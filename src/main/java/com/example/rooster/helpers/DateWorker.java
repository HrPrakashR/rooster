package com.example.rooster.helpers;

import com.example.rooster.period.DateDTO;
import com.example.rooster.period.PeriodDTO;
import com.example.rooster.period.Purpose;
import com.example.rooster.team.Team;

import java.util.*;

public class DateWorker {

    public static String convertDateToTimeString(Date date) {
        Calendar calendar = getCalendarObject(date);
        int hours = calendar.get(Calendar.HOUR_OF_DAY);
        int minutes = calendar.get(Calendar.MINUTE);
        return String.format("%02d:%02d", hours, minutes);
    }

    public static String convertDateToDateString(Date date) {
        Calendar calendar = getCalendarObject(date);
        int minutes = calendar.get(Calendar.MINUTE);
        int hours = calendar.get(Calendar.HOUR_OF_DAY);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        return String.format("%04d-%02d-%02dT%02d:%02d", year, month, day, hours, minutes);
    }

    public static Date convertDateStringToDate(String dateTime) {
        int year = Integer.parseInt(dateTime.substring(0, 4));
        int month = Integer.parseInt(dateTime.substring(5, 7));
        int day = Integer.parseInt(dateTime.substring(8, 10));
        int hours = Integer.parseInt(dateTime.substring(11, 13));
        int minutes = Integer.parseInt(dateTime.substring(14, 16));
        return getDateObject(0, minutes, hours, day, month, year);
    }

    public static Date getDate(boolean lastDayOfMonth, int year, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, lastDayOfMonth ? calendar.getActualMaximum(Calendar.DAY_OF_MONTH) : calendar.getActualMinimum(Calendar.DAY_OF_MONTH));

        calendar.set(Calendar.AM_PM, lastDayOfMonth ? Calendar.PM : Calendar.AM);
        calendar.set(Calendar.MILLISECOND, lastDayOfMonth ? calendar.getActualMaximum(Calendar.MILLISECOND) : calendar.getActualMinimum(Calendar.MILLISECOND));
        calendar.set(Calendar.SECOND, lastDayOfMonth ? calendar.getActualMaximum(Calendar.SECOND) : calendar.getActualMinimum(Calendar.SECOND));
        calendar.set(Calendar.MINUTE, lastDayOfMonth ? calendar.getActualMaximum(Calendar.MINUTE) : calendar.getActualMinimum(Calendar.MINUTE));
        calendar.set(Calendar.HOUR, lastDayOfMonth ? calendar.getActualMaximum(Calendar.HOUR) : calendar.getActualMinimum(Calendar.HOUR));

        return calendar.getTime();
    }

    public static List<Calendar> getAllDaysOfMonth(int year, int month) {
        List<Calendar> days = new ArrayList<>();

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        int i = 1;
        while (i <= calendar.getActualMaximum(Calendar.DAY_OF_MONTH)) {
            days.add(calendar);
            calendar.set(Calendar.DAY_OF_MONTH, ++i);
        }
        return days;
    }

    public static List<Calendar> removeDays(List<Calendar> calendarInput, boolean monday, boolean tuesday, boolean wednesday, boolean thursday, boolean friday, boolean saturday, boolean sunday) {
        List<Calendar> days = new ArrayList<>();
        calendarInput.forEach(day -> {
                    switch (day.get(Calendar.DAY_OF_WEEK)) {
                        case 1:
                            if (!sunday) days.add(day);
                            break;
                        case 2:
                            if (!monday) days.add(day);
                            break;
                        case 3:
                            if (!tuesday) days.add(day);
                            break;
                        case 4:
                            if (!wednesday) days.add(day);
                            break;
                        case 5:
                            if (!thursday) days.add(day);
                            break;
                        case 6:
                            if (!friday) days.add(day);
                            break;
                        case 7:
                            if (!saturday) days.add(day);
                            break;
                        default:
                            throw new IllegalStateException("Unexpected value: " + day.get(Calendar.DAY_OF_WEEK));
                    }
                }
        );
        return days;
    }

    public static Date getDateObject(int seconds, int minutes, int hours, int day, int month, int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.SECOND, seconds);
        calendar.set(Calendar.MINUTE, minutes);
        calendar.set(Calendar.HOUR_OF_DAY, hours);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.YEAR, year);
        return calendar.getTime();
    }

    public static Date getDateObject(int seconds, int minutes, int hours) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.SECOND, seconds);
        calendar.set(Calendar.MINUTE, minutes);
        calendar.set(Calendar.HOUR_OF_DAY, hours);
        return calendar.getTime();
    }

    public static Date getDateObject(int year, int month, boolean dayMax) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayMax? calendar.getActualMaximum(Calendar.DAY_OF_MONTH) : calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        return calendar.getTime();
    }

    public static Date getDateObjectYMD(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH,day);
        return calendar.getTime();
    }

    public static Calendar getCalendarObject(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    private List<DateDTO> getWorkingPeriods(Team team, int year, int month) {
        List<DateDTO> workingPeriods = new ArrayList<>();
        List<Calendar> allDays = DateWorker.getAllDaysOfMonth(year, month);

        // if times are the same, at that day is no working day
        boolean monday = team.getMondayFrom() == team.getMondayTo();
        boolean tuesday = team.getTuesdayFrom() == team.getTuesdayTo();
        boolean wednesday = team.getWednesdayFrom() == team.getWednesdayTo();
        boolean thursday = team.getThursdayFrom() == team.getThursdayTo();
        boolean friday = team.getFridayFrom() == team.getFridayTo();
        boolean saturday = team.getSaturdayFrom() == team.getSaturdayTo();
        boolean sunday = team.getSundayFrom() == team.getSundayTo();

        List<Calendar> workingTime = DateWorker.removeDays(allDays, monday, tuesday, wednesday, thursday, friday, saturday, sunday);

        workingTime.forEach(day -> workingPeriods.add(getDateDTOForWorkingPeriod(day, team)));

        return workingPeriods;
    }

    public DateDTO getDateDTOForWorkingPeriod(Calendar date, Team team) {
        Date getFrom;
        Date getTo;

        switch (date.get(Calendar.DAY_OF_WEEK)) {
            case 1 -> {
                getFrom = team.getSundayFrom();
                getTo = team.getSundayTo();
            }
            case 2 -> {
                getFrom = team.getMondayFrom();
                getTo = team.getMondayTo();
            }
            case 3 -> {
                getFrom = team.getTuesdayFrom();
                getTo = team.getTuesdayTo();
            }
            case 4 -> {
                getFrom = team.getWednesdayFrom();
                getTo = team.getWednesdayTo();
            }
            case 5 -> {
                getFrom = team.getThursdayFrom();
                getTo = team.getThursdayTo();
            }
            case 6 -> {
                getFrom = team.getFridayFrom();
                getTo = team.getFridayTo();
            }
            case 7 -> {
                getFrom = team.getSaturdayFrom();
                getTo = team.getSaturdayTo();
            }
            default -> throw new IllegalStateException("Unexpected value: " + date.get(Calendar.DAY_OF_WEEK));
        }

        return new DateDTO(
                DateWorker.getDateObject(
                        DateWorker.getCalendarObject(getFrom).get(Calendar.SECOND),
                        DateWorker.getCalendarObject(getFrom).get(Calendar.MINUTE),
                        DateWorker.getCalendarObject(getFrom).get(Calendar.HOUR_OF_DAY),
                        date.get(Calendar.DAY_OF_MONTH),
                        date.get(Calendar.MONTH),
                        date.get(Calendar.YEAR)
                ),
                DateWorker.getDateObject(
                        DateWorker.getCalendarObject(getTo).get(Calendar.SECOND),
                        DateWorker.getCalendarObject(getTo).get(Calendar.MINUTE),
                        DateWorker.getCalendarObject(getTo).get(Calendar.HOUR_OF_DAY),
                        date.get(Calendar.DAY_OF_MONTH),
                        date.get(Calendar.MONTH),
                        date.get(Calendar.YEAR)
                )
        );
    }

    public static double calculateHours(String dateFrom, String dateTo) {
        Date from = DateWorker.convertDateStringToDate(dateFrom);
        Date to = DateWorker.convertDateStringToDate(dateTo);
        double diff = to.getTime() - from.getTime();
        return diff / (1000 * 60 * 60);
    }

    public static Double getWorkingTime(List<PeriodDTO> workingHours, Double dailyWorkingHours){

        if (workingHours.isEmpty()) {
            return 0.0;
        }

        double wh = workingHours
                .stream()
                .filter(period ->
                        Objects.equals(period.getPurpose(), Purpose.CONFIRMED_VACATION.name()) ||
                                Objects.equals(period.getPurpose(), Purpose.SICK_LEAVE.name())
                )
                .mapToDouble(period -> dailyWorkingHours)
                .sum();

        wh += workingHours
                .stream()
                .filter(period -> Objects.equals(period.getPurpose(), Purpose.WORKING_HOURS.name()))
                .mapToDouble(period -> DateWorker.calculateHours(period.getDateFrom(), period.getDateTo()))
                .sum();

        return wh;
    }
}


