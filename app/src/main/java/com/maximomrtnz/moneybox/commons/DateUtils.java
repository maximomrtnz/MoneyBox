package com.maximomrtnz.moneybox.commons;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by Maxi on 2/18/2017.
 */

public class DateUtils {

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

}
