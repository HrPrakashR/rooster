package com.example.rooster.date_worker;

import java.util.Calendar;
import java.util.Date;

public class DateWorker {
    public static Date getDate(boolean lastDayOfMonth, int year, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, lastDayOfMonth ? calendar.getActualMaximum(Calendar.DAY_OF_MONTH) : calendar.getActualMinimum(Calendar.DAY_OF_MONTH));

        calendar.set(Calendar.AM_PM, lastDayOfMonth ? Calendar.PM : Calendar.AM);
        calendar.set(Calendar.MILLISECOND, lastDayOfMonth? calendar.getActualMaximum(Calendar.MILLISECOND) : calendar.getActualMinimum(Calendar.MILLISECOND));
        calendar.set(Calendar.SECOND, lastDayOfMonth? calendar.getActualMaximum(Calendar.SECOND) : calendar.getActualMinimum(Calendar.SECOND));
        calendar.set(Calendar.MINUTE, lastDayOfMonth? calendar.getActualMaximum(Calendar.MINUTE) : calendar.getActualMinimum(Calendar.MINUTE));
        calendar.set(Calendar.HOUR, lastDayOfMonth? calendar.getActualMaximum(Calendar.HOUR) : calendar.getActualMinimum(Calendar.HOUR));

        return calendar.getTime();
    }
}
