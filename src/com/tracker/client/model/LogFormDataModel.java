package com.tracker.client.model;

import com.google.gwt.json.client.*;
import com.google.gwt.storage.client.Storage;
import com.google.gwt.user.client.Window;

import java.util.Date;

public class LogFormDataModel {

    private String tableNameInLocalStorage;
    private Long startTime;
    private Long duration;
    private String title;
    private String description;
    private Storage localStorage = Storage.getLocalStorageIfSupported();
    private static LogFormDataModel instance;

    public static synchronized LogFormDataModel getInstance(){
        if (instance == null){
            instance = new LogFormDataModel();
        }
        return instance;
    }

    private LogFormDataModel(){
        tableNameInLocalStorage = "TrackerLoggerFormData";
        setBaseData();
        fetchDataFromStorage();
    }

    public void setStartTime(Long startTime){
        this.startTime = startTime;
        saveDataToStorage();
    }

    public void setDuration(Long duration){
        this.duration = duration;
        saveDataToStorage();
    }

    public void setTitle(String title){
        this.title = title;
        saveDataToStorage();
    }

    public void setDescription(String description){
        this.description = description;
        saveDataToStorage();
    }

    public Long getStartTime(){ return this.startTime; }

    public Long getDuration(){ return this.duration; }

    public String getTitle(){
        return this.title;
    }

    public String getDescription(){
        return this.description;
    }

    public void fetchDataFromStorage(){
        if(localStorage != null){
            if(localStorage.getItem(tableNameInLocalStorage) != null) {
                JSONValue jsonValue = JSONParser.parse(localStorage.getItem(tableNameInLocalStorage));
                JSONObject jsonObject = jsonValue.isObject();
                startTime = Long.decode(jsonObject.get("startTime").isNumber().toString());
                duration = Long.decode(jsonObject.get("duration").isNumber().toString());
                title = jsonObject.get("title").isString().stringValue();
                description = jsonObject.get("description").isString().stringValue();
            }
        }
    }

    public void saveDataToStorage(){
        if(localStorage != null){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("startTime", new JSONNumber(startTime));
            jsonObject.put("duration", new JSONNumber(duration));
            jsonObject.put("title", new JSONString(title));
            jsonObject.put("description", new JSONString(description));
            localStorage.setItem(tableNameInLocalStorage, jsonObject.toString());
        }
    }

    public void clear(){
        setBaseData();
        removeDataFromStorage();
    }

    private void setBaseData(){
        startTime = new Date().getTime();
        duration = new Long(0);
        title = "";
        description = "";
    };

    private void removeDataFromStorage(){
        if(localStorage != null) {
            localStorage.removeItem(tableNameInLocalStorage);
        }
    }
}
