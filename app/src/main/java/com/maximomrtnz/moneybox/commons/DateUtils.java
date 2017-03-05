package com.maximomrtnz.moneybox.commons;

import android.content.Context;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * Created by Maxi on 2/18/2017.
 */

public class DateUtils {

    public static String getMonthName(Context context, Calendar c){
        Locale current = context.getResources().getConfiguration().locale;
        return String.format(current,"%tB",c);
    }

    public static Calendar getCalendarFromTimeInMillis(Long timeinmillis){
        Calendar calendar = new GregorianCalendar();
        if(timeinmillis!=null) {
            calendar.setTimeInMillis(timeinmillis);
        }
        return calendar;
    }

    public static String calendarToString(Calendar calendar, String pattern){
        // TODO: Show Date format using format from user's time
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.format(calendar.getTime());
    }

    public static Long getCurrentTime(){
        Calendar calendar = new GregorianCalendar();
        return calendar.getTimeInMillis();
    }

    public static Long getFirstDate(int year, int month){
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DATE, 1);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.YEAR, year);

        cal.set(Calendar.DAY_OF_MONTH, 1);
        return cal.getTimeInMillis();
    }


    public static Long getLastDate(int year, int month){
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.YEAR, year);

        cal.set(Calendar.DAY_OF_MONTH, 1);// This is necessary to get proper results
        cal.set(Calendar.DATE, cal.getActualMaximum(Calendar.DATE));

        return cal.getTimeInMillis();
    }

}
