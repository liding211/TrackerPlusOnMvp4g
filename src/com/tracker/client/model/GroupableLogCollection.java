package com.tracker.client.model;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Window;
import com.googlecode.gwt.charts.client.controls.filter.StringFilter;

import java.util.*;

public class GroupableLogCollection {

    public static final int GROUP_BY_DAY = 0;
    public static final int GROUP_BY_WEEK = 1;
    private List<LogModel> logCollection;

    public GroupableLogCollection( List logCollection ){
        this.logCollection = logCollection;
    }

    public void setGroupingByPeriod( int period ){
        switch( period ) {
            case GROUP_BY_WEEK:
                groupByWeek();
                break;
            case GROUP_BY_DAY:
            default:
                groupByDay();
                break;
        }
    }

    public List<LogModel> getGroupedCollection(){
        return logCollection;
    }

    private void groupByDay(){
        groupByPattern( "YYYYMMDD" );
    }

    private void groupByWeek(){
        groupByPattern( "YYYYWW" );
    }

    private void groupByPattern( String pattern ){
        if(logCollection.size() > 0){
            HashMap<String, LogModel> tempCollection = new HashMap<String, LogModel>();
            for( LogModel log : logCollection ){
                String date = DateTimeFormat.getFormat( pattern ).format( new Date( log.getStartTime() ) );
                LogModel tempLog = tempCollection.get( (Object) date ) != null ?
                        tempCollection.get( (Object) date ) : new LogModel();
                tempLog.setStartTime( (new Date( log.getStartTime() )).getTime() );
                Long duration = tempLog.getDuration() + log.getDuration();
                tempLog.setDuration( duration );
                tempCollection.put( date, tempLog);
            }

            SortedSet<String> keys = new TreeSet<String>(tempCollection.keySet());
            logCollection.clear();
            for (String key : keys) {
                logCollection.add( tempCollection.get(key) );
            }
        }
    }
}
