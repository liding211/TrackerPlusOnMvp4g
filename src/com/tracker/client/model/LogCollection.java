package com.tracker.client.model;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.json.client.*;
import com.google.gwt.storage.client.Storage;
import com.google.gwt.user.client.Window;

import java.util.*;

public class LogCollection {

    private ArrayList<LogModel> logCollection;
    private String tableNameInLocalStorage;
    private Storage localStorage;
    private static LogCollection instance;
    public static long PERIOD_SEVEN_DAYS;
    public static long PERIOD_ONE_MONTH;
    public static long PERIOD_THREE_MONTHS;
    public static String GROUP_BY_DAY_PATTERN = "yyyyMMdd";
    public static String GROUP_BY_WEEK_PATTERN = "yyyy";

    public static synchronized LogCollection getInstance(){
        if(instance == null){
            instance = new LogCollection();
        }
        return instance;
    }

    private LogCollection(){
        tableNameInLocalStorage = "TrackerLogsCollection";
        localStorage = Storage.getLocalStorageIfSupported();
        logCollection = new ArrayList();

        long dayInMillis = 24 * 60 * 60 * 1000;
        PERIOD_SEVEN_DAYS = 7 * dayInMillis;
        PERIOD_ONE_MONTH = daysInMonth() * dayInMillis;
        PERIOD_THREE_MONTHS = 3 * PERIOD_ONE_MONTH;
    }

    public void fetchAllLogs(){
        if(localStorage != null){
            if(localStorage.getItem(tableNameInLocalStorage) != null) {
                logCollection.clear();
                JSONValue jsonValue = JSONParser.parse(localStorage.getItem(tableNameInLocalStorage));
                JSONObject jsonObject = jsonValue.isObject();

                for( String key : jsonObject.keySet()){
                    LogModel log = new LogModel();
                    String itemKey = log.getKeyWithPrefix( Long.decode(key) );
                    if(localStorage.getItem(itemKey) != null) {
                        JSONValue keyJsonValue = JSONParser.parse(localStorage.getItem(itemKey));
                        JSONObject keyJsonObject = keyJsonValue.isObject();
                        log.setTitle( keyJsonObject.get("title").isString().stringValue() );
                        log.setDescription( keyJsonObject.get("description").isString().stringValue() );
                        log.setDuration( Long.decode( keyJsonObject.get("duration").isNumber().toString() ) );
                        log.setStartTime( Long.decode( keyJsonObject.get("startTime").isNumber().toString() ) );
                        logCollection.add(log);
                    }
                }
            }
        }
    }

    public void addLogToCollection( Long key ){
        if(localStorage != null){
            JSONValue jsonValue;
            JSONObject jsonObject;

            if( localStorage.getItem(tableNameInLocalStorage) != null ){
                jsonValue = JSONParser.parse( localStorage.getItem(tableNameInLocalStorage) );
            } else {
                jsonValue = new JSONObject();
            }

            jsonObject = jsonValue.isObject() != null ? jsonValue.isObject() : new JSONObject();
            jsonObject.put( key.toString(), new JSONString(key.toString()));

            localStorage.setItem(tableNameInLocalStorage, jsonObject.toString());
        }
    }

    public void removeLogFromCollection( String key ){

    }

    public List<LogModel> getLogCollection(){
        fetchAllLogs();
        return logCollection;
    }

    private native int daysInMonth()/*-{
        var date = new Date();
        return new Date(date.getYear(), date.getMonth(), 0).getDate();
    }-*/;

    private static native int getWeekNumber( int timeStamp)/*-{
        var d = new Date(timeStamp);
        d.setHours(0,0,0);
        d.setDate(d.getDate()+4-(d.getDay()||7));
        return Math.ceil((((d-new Date(d.getFullYear(),0,1))/8.64e7)+1)/7);
    }-*/;

    public static List<LogModel> getGroupCollectionByPattern( List<LogModel> logCollection, String pattern ){

        List<LogModel> collection = new ArrayList<LogModel>();
        if(logCollection.size() > 0){
            HashMap<String, LogModel> tempCollection = new HashMap<String, LogModel>();
            for( LogModel log : logCollection ){
                String date = DateTimeFormat.getFormat(pattern).format( new Date( log.getStartTime() ) );
                if( pattern == GROUP_BY_WEEK_PATTERN ){
                    //GWT DateTimeFormat not support standarted of base java Date class
                    date += getWeekNumber( log.getStartTime().intValue() );
                }
                LogModel tempLog = tempCollection.get( (Object) date ) != null ?
                        tempCollection.get( (Object) date ) : new LogModel();
                tempLog.setStartTime( (new Date( log.getStartTime() )).getTime() );
                Long duration = tempLog.getDuration() + log.getDuration();
                tempLog.setDuration( duration );
                tempCollection.put( date, tempLog);
            }

            SortedSet<String> keys = new TreeSet<String>(tempCollection.keySet());

            for (String key : keys) {
                collection.add( tempCollection.get(key) );
            }
        }
        return collection;
    }

    public static List<LogModel> getCollectionByPeriod( List<LogModel> logCollection, Long period ){

        List<LogModel> collection = new ArrayList<LogModel>();
        Long startTimeStampDate = new Date().getTime() - period;
        for( LogModel log : logCollection ){
            if( log.getStartTime() > startTimeStampDate ){
                collection.add(log);
            }
        }
        return collection;
    }
}
