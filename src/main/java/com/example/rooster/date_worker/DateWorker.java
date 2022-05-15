package com.example.rooster.date_worker;

import com.example.rooster.period.DateDTO;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DateWorker {
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

    public static List<Calendar> getAllDaysOfMonth(int year, int month){
        List<Calendar> days = new ArrayList<>();

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        int i = 1;
        while(i <= calendar.getActualMaximum(Calendar.DAY_OF_MONTH)){
            days.add(calendar);
            calendar.set(Calendar.DAY_OF_MONTH, ++i);
        }
        return days;
    }

    public static List<Calendar> removeDays(List<Calendar> calendarInput, boolean monday, boolean tuesday, boolean wednesday, boolean thursday, boolean friday, boolean saturday, boolean sunday){
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
                            if(!wednesday) days.add(day);
                            break;
                        case 5:
                            if(!thursday) days.add(day);
                            break;
                        case 6:
                            if(!friday) days.add(day);
                            break;
                        case 7:
                            if(!saturday) days.add(day);
                            break;
                        default:
                            throw new IllegalStateException("Unexpected value: " + day.get(Calendar.DAY_OF_WEEK));
                    };
                }
        );
        return days;
    }

    public static Date getDateObject(int seconds, int minutes, int hours, int day, int month, int year){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.SECOND, seconds);
        calendar.set(Calendar.MINUTE, minutes);
        calendar.set(Calendar.HOUR_OF_DAY, hours);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.YEAR, year);
        return calendar.getTime();
    }

    public static Calendar getCalendarObject(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }
}
