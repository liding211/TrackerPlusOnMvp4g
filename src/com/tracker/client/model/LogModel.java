package com.tracker.client.model;

import com.google.gwt.json.client.*;
import com.google.gwt.storage.client.Storage;

public class LogModel {

    private Long startTime;
    private Long duration;
    private String title;
    private String description;
    private LogCollection logCollection;
    private Storage localStorage;
    private final String logKeyPrefix = "TrackerLog";

    public LogModel(){
        startTime = new Long(0);
        duration = new Long(0);
        title = "";
        description = "";
        logCollection = LogCollection.getInstance();
        localStorage = Storage.getLocalStorageIfSupported();
    }

    public LogModel(Long startTime, Long duration, String title, String description) {
        logCollection = LogCollection.getInstance();
        localStorage = Storage.getLocalStorageIfSupported();
        this.startTime = startTime;
        this.duration = duration;
        this.title = title;
        this.description = description;
        saveDataToStorage();
    }

    public void setStartTime(Long startTime){
        this.startTime = startTime;
    }

    public void setDuration(Long duration){ this.duration = duration; }

    public void setTitle(String title){
        this.title = title;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public Long getStartTime(){
        return this.startTime;
    }

    public Long getDuration(){
        return this.duration;
    }

    public String getTitle(){ return this.title; }

    public String getDescription(){
        return this.description;
    }

    public void saveDataToStorage(){
        if(localStorage != null){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("startTime", new JSONNumber(startTime));
            jsonObject.put("duration", new JSONNumber(duration));
            jsonObject.put("title", new JSONString(title));
            jsonObject.put("description", new JSONString(description));

            String key = logKeyPrefix.concat( startTime.toString() );
            localStorage.setItem(key, jsonObject.toString());
            logCollection.addLogToCollection( startTime );
        }
    }

    public void fetchLogByKey( Long startTime ){
        if(localStorage != null){
            String key = logKeyPrefix.concat( startTime.toString() );
            if(localStorage.getItem(key) != null) {
                JSONValue jsonValue = JSONParser.parse( localStorage.getItem(key) );
                JSONObject jsonObject = jsonValue.isObject();
                this.startTime = Long.decode( jsonObject.get("startTime").isNumber().toString() );
                duration = Long.decode( jsonObject.get("duration").isNumber().toString() );
                title = jsonObject.get("title").isString().stringValue();
                description = jsonObject.get("description").isString().stringValue();
            }
        }
    }

    public void removeLogFromStorage(){
        if(localStorage != null) {
            String key = logKeyPrefix.concat( startTime.toString() );
            logCollection.removeLogFromCollection( startTime.toString() );
            localStorage.removeItem(key);
        }
    }

    public String getKeyWithPrefix( Long startTime ){
        return logKeyPrefix.concat( startTime.toString() );
    }
}
