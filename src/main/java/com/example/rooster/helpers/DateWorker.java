package com.example.rooster.helpers;

import com.example.rooster.period.PeriodDTO;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.IntStream;

/**
 * Static utility methods to work with dates and times
 */
public class DateWorker {

    /**
     * Converts a Date object into Time String (angular-frontend compatible)
     *
     * @param date any Date object
     * @return String in String.format("%02d:%02d", hours, minutes)
     */
    public static String convertDateToTimeString(Date date) {
        Calendar calendar = convertDateToCalendarObject(date);
        return convertCalendarToTimeString(calendar);
    }

    /**
     * Converts a Calendar object to Time String
     *
     * @param calendar any Calendar objecct
     * @return String in String.format("%02d:%02d", hours, minutes)
     */
    public static String convertCalendarToTimeString(Calendar calendar) {
        int hours = calendar.get(Calendar.HOUR_OF_DAY);
        int minutes = calendar.get(Calendar.MINUTE);
        return String.format("%02d:%02d", hours, minutes);
    }

    /**
     * Converts a Date object into Date String
     *
     * @param date any Date object
     * @return String in String.format("%04d-%02d-%02dT%02d:%02d", year, month, day, hours, minutes)
     */
    public static String convertDateToDateString(Date date) {
        Calendar calendar = convertDateToCalendarObject(date);
        return convertCalendarToDateString(calendar);
    }

    /**
     * Converts a Calendar object into String
     *
     * @param calendar any Calendar object
     * @return String in String.format("%04d-%02d-%02dT%02d:%02d", year, month, day, hours, minutes)
     */
    public static String convertCalendarToDateString(Calendar calendar) {
        int minutes = calendar.get(Calendar.MINUTE);
        int hours = calendar.get(Calendar.HOUR_OF_DAY);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        return String.format("%04d-%02d-%02dT%02d:%02d", year, month, day, hours, minutes);
    }

    /**
     * converts a Date String into Date object
     *
     * @param dateTime a String with String.format("%04d-%02d-%02dT%02d:%02d", year, month, day, hours, minutes)
     * @return Calendar object
     */
    public static Date convertDateStringToDate(String dateTime) {
        int year = getIntFromSubstring(dateTime, 0, 4);
        int month = getIntFromSubstring(dateTime, 5, 7);
        int day = getIntFromSubstring(dateTime, 8, 10);
        int hours = getIntFromSubstring(dateTime, 11, 13);
        int minutes = getIntFromSubstring(dateTime, 14, 16);
        return getDateObject(0, minutes, hours, day, month, year);
    }

    /**
     * Converts a Date into Calendar object
     *
     * @param date Any Date object
     * @return Calendar object
     */
    public static Calendar convertDateToCalendarObject(Date date) {
            Calendar calendar = Calendar.getInstance();
            if(date != null) calendar.setTime(date);
            return calendar;
    }

    /**
     * Takes a String and converts the substring into integer
     *
     * @param string a String with numbers
     * @param start  substring beginning
     * @param end    substring ends
     * @return integer
     */
    private static int getIntFromSubstring(String string, int start, int end) {
        return Integer.parseInt(string.substring(start, end));
    }

    /**
     * Returns a List with Calendar objects with all days of a specific month
     *
     * @param year  Year
     * @param month month
     * @return List with Calendar objects
     */
    public static List<Calendar> getAllDaysOfMonth(int year, int month) {
        List<Calendar> days = new ArrayList<>();

        Calendar calendar = getCalendarObject(0, 0, 0, 1, month, year);
        IntStream.rangeClosed(1, calendar.getActualMaximum(Calendar.DAY_OF_MONTH))
                .forEachOrdered(i -> {
                    days.add(calendar);
                    calendar.set(Calendar.DAY_OF_MONTH, i);
                });
        return days;
    }

    /**
     * Returns the first or last day of the month as Calendar Object
     *
     * @param lastDayOfMonth true = last day / false = first day
     * @param year           Year
     * @param month          Month
     * @return Calendar object
     */
    public static Calendar getFirstOrLastDayOfMonth(boolean lastDayOfMonth, int year, int month) {
        Calendar calendar = getCalendarObject(0, 0, 0, 0, month, year);
        calendar.set(Calendar.DAY_OF_MONTH, lastDayOfMonth ? calendar.getActualMaximum(Calendar.DAY_OF_MONTH) : calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        calendar.set(Calendar.AM_PM, lastDayOfMonth ? Calendar.PM : Calendar.AM);
        calendar.set(Calendar.MILLISECOND, lastDayOfMonth ? calendar.getActualMaximum(Calendar.MILLISECOND) : calendar.getActualMinimum(Calendar.MILLISECOND));
        calendar.set(Calendar.SECOND, lastDayOfMonth ? calendar.getActualMaximum(Calendar.SECOND) : calendar.getActualMinimum(Calendar.SECOND));
        calendar.set(Calendar.MINUTE, lastDayOfMonth ? calendar.getActualMaximum(Calendar.MINUTE) : calendar.getActualMinimum(Calendar.MINUTE));
        calendar.set(Calendar.HOUR, lastDayOfMonth ? calendar.getActualMaximum(Calendar.HOUR) : calendar.getActualMinimum(Calendar.HOUR));
        return calendar;
    }

    /**
     * Creates a Calendar object with predefined values
     *
     * @param seconds Seconds
     * @param minutes Minutes
     * @param hours   Hours
     * @param day     Day
     * @param month   Month
     * @param year    Year
     * @return Calendar object
     */
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

    /**
     * Creates a Date object with predefined values
     *
     * @param seconds Seconds
     * @param minutes Minutes
     * @param hours   Hours
     * @param day     Day
     * @param month   Month
     * @param year    Year
     * @return Date object
     */
    public static Date getDateObject(int seconds, int minutes, int hours, int day, int month, int year) {
        return getCalendarObject(seconds, minutes, hours, day, month, year).getTime();
    }

    /**
     * Creates a Date object with predefined values
     *
     * @param seconds Seconds
     * @param minutes Minutes
     * @param hours   Hours
     * @return Date object
     */
    public static Date getDateObject(int seconds, int minutes, int hours) {
        return getCalendarObject(seconds, minutes, hours, 0, 0, 0).getTime();
    }

    /**
     * Creates a Date object with predefined values and first or last day of a month
     *
     * @param year      Year
     * @param month     Month
     * @param isLastDay true = last day / false = first day
     * @return Date object
     */
    public static Date getDateObject(int year, int month, boolean isLastDay) {
        Calendar calendar = getCalendarObject(0, 0, 0, 0, month, year);
        calendar.set(Calendar.DAY_OF_MONTH, isLastDay ? calendar.getActualMaximum(Calendar.DAY_OF_MONTH) : calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        return calendar.getTime();
    }

    /**
     * Calculates hours between two DateStrings.
     * You can use the implemented Date and String methods to work with those.
     *
     * @param dateFrom DateString with String.format("%04d-%02d-%02dT%02d:%02d", year, month, day, hours, minutes)
     * @param dateTo   DateString with String.format("%04d-%02d-%02dT%02d:%02d", year, month, day, hours, minutes)
     * @return Double number: hours of difference
     */
    public static double calculateHours(String dateFrom, String dateTo) {
        Date from = DateWorker.convertDateStringToDate(dateFrom);
        Date to = DateWorker.convertDateStringToDate(dateTo);
        double diff = to.getTime() - from.getTime();
        return diff / (1000 * 60 * 60);
    }

    /**
     * Counts all days of a specific month
     *
     * @param year  Year
     * @param month Month
     * @return Integer with number of days in a month
     */
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

    /**
     * Creates a Date string with String.format("%04d-%02d-%02d", year, month, day)
     *
     * @param day   Day
     * @param month Month
     * @param year  Year
     * @return String with String.format("%04d-%02d-%02d", year, month, day)
     */
    public static String createDateString(int day, int month, int year) {
        return String.format("%04d-%02d-%02d", year, month, day);
    }

    /**
     * Checks if a PeriodDTO contains a specific date
     *
     * @param periodDTO PeriodDTO
     * @param day       Day
     * @param month     Month
     * @param year      Year
     * @return boolean
     */
    public static boolean checkIfPeriodDTOContainsDate(PeriodDTO periodDTO, int day, int month, int year) {
        return periodDTO.getDateFrom().startsWith(createDateString(day, month, year))
                || periodDTO.getDateTo().startsWith(createDateString(day, month, year));
    }
}
