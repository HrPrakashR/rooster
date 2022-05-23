package com.example.rooster.helpers;

import com.example.rooster.period.DateDTO;
import com.example.rooster.period.PeriodDTO;
import com.example.rooster.team.Team;

import java.lang.constant.Constable;
import java.util.*;
import java.util.stream.IntStream;

public class DateWorker {

    public static String convertDateToTimeString(Date date) {
        Calendar calendar = convertDateToCalendarObject(date);
        return convertCalendarToTimeString(calendar);
    }

    public static String convertCalendarToTimeString(Calendar calendar){
        int hours = calendar.get(Calendar.HOUR_OF_DAY);
        int minutes = calendar.get(Calendar.MINUTE);
        return String.format("%02d:%02d", hours, minutes);
    }

    public static String convertDateToDateString(Date date) {
        Calendar calendar = convertDateToCalendarObject(date);
        return convertCalendarToDateString(calendar);
    }

    public static String  convertCalendarToDateString(Calendar calendar){
        int minutes = calendar.get(Calendar.MINUTE);
        int hours = calendar.get(Calendar.HOUR_OF_DAY);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        return String.format("%04d-%02d-%02dT%02d:%02d", year, month, day, hours, minutes);
    }

    public static Date convertDateStringToDate(String dateTime) {
        int year = getIntFromSubstring(dateTime, 0, 4);
        int month = getIntFromSubstring(dateTime, 5, 7);
        int day = getIntFromSubstring(dateTime, 8, 10);
        int hours = getIntFromSubstring(dateTime, 11, 13);
        int minutes = getIntFromSubstring(dateTime, 14, 16);
        return getDateObject(0, minutes, hours, day, month, year);
    }

    public static Calendar convertDateStringToCalendar(String dateTime){
        return convertDateToCalendarObject(convertDateStringToDate(dateTime));
    }

    private static int getIntFromSubstring(String string, int start, int end) {
        return Integer.parseInt(string.substring(start, end));
    }

    public static List<Calendar> getAllDaysOfMonth(int year, int month) {
        List<Calendar> days = new ArrayList<>();

        Calendar calendar = getCalendarObject(0,0,0,0,year,month);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        IntStream.rangeClosed(1, calendar.getActualMaximum(Calendar.DAY_OF_MONTH))
                .forEachOrdered(i -> {
                    days.add(calendar);
                    calendar.set(Calendar.DAY_OF_MONTH, i);
                });
        return days;
    }

    public static List<Calendar> removeDays(List<Calendar> calendarInput,
                                            boolean monday,
                                            boolean tuesday,
                                            boolean wednesday,
                                            boolean thursday,
                                            boolean friday,
                                            boolean saturday,
                                            boolean sunday) {
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

    public static Calendar getFirstOrLastDayOfMonth(boolean lastDayOfMonth, int year, int month) {
        Calendar calendar = getCalendarObject(0,0,0,0,month,year);
        calendar.set(Calendar.DAY_OF_MONTH, lastDayOfMonth ? calendar.getActualMaximum(Calendar.DAY_OF_MONTH) : calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        calendar.set(Calendar.AM_PM, lastDayOfMonth ? Calendar.PM : Calendar.AM);
        calendar.set(Calendar.MILLISECOND, lastDayOfMonth ? calendar.getActualMaximum(Calendar.MILLISECOND) : calendar.getActualMinimum(Calendar.MILLISECOND));
        calendar.set(Calendar.SECOND, lastDayOfMonth ? calendar.getActualMaximum(Calendar.SECOND) : calendar.getActualMinimum(Calendar.SECOND));
        calendar.set(Calendar.MINUTE, lastDayOfMonth ? calendar.getActualMaximum(Calendar.MINUTE) : calendar.getActualMinimum(Calendar.MINUTE));
        calendar.set(Calendar.HOUR, lastDayOfMonth ? calendar.getActualMaximum(Calendar.HOUR) : calendar.getActualMinimum(Calendar.HOUR));

        return calendar;
    }

    public static Calendar getCalendarObject(int seconds, int minutes, int hours, int day, int month, int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.SECOND, seconds);
        calendar.set(Calendar.MINUTE, minutes);
        calendar.set(Calendar.HOUR_OF_DAY, hours);

        if (day != 0) {
            calendar.set(Calendar.DAY_OF_MONTH, day);
        }
        if (month != 0) {
            calendar.set(Calendar.MONTH, month);
        }
        if (year != 0) {
            calendar.set(Calendar.YEAR, year);
        }

        return calendar;
    }

    public static Date getDateObject(int seconds, int minutes, int hours, int day, int month, int year) {
        return getCalendarObject(seconds, minutes, hours, day, month, year).getTime();
    }

    public static Date getDateObject(int seconds, int minutes, int hours) {
        return getCalendarObject(seconds, minutes, hours, 0, 0, 0).getTime();
    }

    public static Date getDateObject(int year, int month, boolean dayMax) {
        Calendar calendar = getCalendarObject(0, 0, 0, 0, month, year);
        calendar.set(Calendar.DAY_OF_MONTH, dayMax ? calendar.getActualMaximum(Calendar.DAY_OF_MONTH) : calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        return calendar.getTime();
    }

    public static Calendar convertDateToCalendarObject(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    public static double calculateHours(String dateFrom, String dateTo) {
        Date from = DateWorker.convertDateStringToDate(dateFrom);
        Date to = DateWorker.convertDateStringToDate(dateTo);
        double diff = to.getTime() - from.getTime();
        return diff / (1000 * 60 * 60);
    }



    public static int countAllWeekdaysOfMonth(int year, int month) {
        Calendar calendar = getFirstOrLastDayOfMonth(false, year, month);
        int counter = 1;
        int day = 1;
        while (day <= calendar.getActualMaximum(Calendar.DAY_OF_MONTH)) {
            int actualDate = getCalendarObject(0, 0, 0, day, month, year).get(Calendar.DAY_OF_WEEK);
            if (actualDate != Calendar.SUNDAY && actualDate != Calendar.SATURDAY) {
                counter++;
            }
            day++;
        }
        return counter;
    }

    public static boolean checkIfTeamWorksAtDay(Team team, int i) {
        switch (i) {
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
                getDateObject(
                        convertDateToCalendarObject(getFrom).get(Calendar.SECOND),
                        convertDateToCalendarObject(getFrom).get(Calendar.MINUTE),
                        convertDateToCalendarObject(getFrom).get(Calendar.HOUR_OF_DAY),
                        date.get(Calendar.DAY_OF_MONTH),
                        date.get(Calendar.MONTH),
                        date.get(Calendar.YEAR)
                ),
                getDateObject(
                        convertDateToCalendarObject(getTo).get(Calendar.SECOND),
                        convertDateToCalendarObject(getTo).get(Calendar.MINUTE),
                        convertDateToCalendarObject(getTo).get(Calendar.HOUR_OF_DAY),
                        date.get(Calendar.DAY_OF_MONTH),
                        date.get(Calendar.MONTH),
                        date.get(Calendar.YEAR)
                )
        );
    }

    public static String createDateString(int day, int month, int year){
        return String.format("%04d-%02d-%02d", year, month, day);
    }

    public static boolean checkIfPeriodDTOContainsDate(PeriodDTO periodDTO, int day, int year, int month){
        return periodDTO.getDateFrom().startsWith(createDateString(day, month, year))
                || periodDTO.getDateTo().startsWith(createDateString(day, month, year));
    }
}
