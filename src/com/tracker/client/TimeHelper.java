package com.tracker.client;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.LocaleInfo;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.i18n.client.constants.DateTimeConstants;
import com.google.gwt.user.client.Window;
import com.tracker.client.model.SettingsModel;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

public abstract class TimeHelper {

    public static String getTimeByTimeStamp( Long timePeriod, SettingsModel settings ){
        String time = "";
        int secondInMs = 1000;
        int minuteInMs = 60 * secondInMs;
        int hourInMs = 60 * minuteInMs;
        int dayInMs = 24 * hourInMs;
        int yearInDay = 365;

        String seconds = NumberFormat.getFormat("00").format( Math.floor( ( timePeriod % minuteInMs ) / secondInMs ) );
        String minutes = NumberFormat.getFormat("00").format( Math.floor( ( timePeriod % hourInMs ) / minuteInMs ) );
        String hours = NumberFormat.getFormat("00").format( Math.floor( ( timePeriod % dayInMs ) / hourInMs ) );
        Double days = Math.floor( timePeriod / dayInMs );

        time = (days < yearInDay) ?
            NumberFormat.getFormat("00").format( days ) + settings.getCurrentDateTimeFormat().getTimeDelimiter() : "";
        time += hours + settings.getCurrentDateTimeFormat().getTimeDelimiter();
        time += minutes + settings.getCurrentDateTimeFormat().getTimeDelimiter();
        time += seconds;

        return time;
    }

    public static String getWeekRangeTitleByDate( Long timeStamp, SettingsModel settings ){

        int dayInMillis = 24 * 60 * 60 * 1000;
        int daysInWeek = 6; // 0 - is first day of week

        Date currentDay = new Date( timeStamp - (timeStamp % dayInMillis));

        DateTimeFormat format = DateTimeFormat.getFormat("c"); // try with "E" pattern also

        int dayOfWeek = Integer.decode( format.format( currentDay ) );

        Date startDate = new Date( ( timeStamp - dayInMillis * dayOfWeek ) - ( timeStamp % dayInMillis ));
        Date endDate = new Date( startDate.getTime() + dayInMillis * daysInWeek );
        Date startDateOfCurrentWeek = new Date( ( new Date().getTime() - dayInMillis * dayOfWeek ) - ( new Date().getTime() % dayInMillis ));

        String startDateString = DateTimeFormat.getFormat( settings.getCurrentDateTimeFormat().getDateFormatForAnalytics() )
                .format( startDate );
        String endDateString = DateTimeFormat.getFormat( settings.getCurrentDateTimeFormat().getDateFormatForAnalytics() )
                .format( endDate );
        String dateRange = startDateString + " - " + endDateString;
        if (startDateOfCurrentWeek.getTime() == startDate.getTime()) {
            dateRange += "*";
        }
        return dateRange;
    }
}
