package com.tracker.client.model;

import com.google.gwt.json.client.*;
import com.google.gwt.storage.client.Storage;

public class LogFormDataModel {

    private String tableNameInLocalStorage = "TrackerLoggerFormData";
    private Long startTime = new Long(0);
    private Long duration = new Long(0);
    private String title = "";
    private String description = "";
    private Storage localStorage = Storage.getLocalStorageIfSupported();
    private static LogFormDataModel instance;

    public static LogFormDataModel getInstance(){
        if (instance == null){
            instance = new LogFormDataModel();
        }
        return instance;
    }

    private LogFormDataModel(){
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

    public void removeDataFromStorage(){
        if(localStorage != null) {
            localStorage.removeItem(tableNameInLocalStorage);
        }
    }

    private void fetchDataFromStorage(){
        if(localStorage != null){
            if(localStorage.getItem(tableNameInLocalStorage) != null) {
                JSONValue jsonValue = JSONParser.parse(localStorage.getItem("TrackerLoggerFormData"));
                JSONObject jsonObject = jsonValue.isObject();
                startTime = Double.doubleToLongBits(jsonObject.get("startTime").isNumber().doubleValue());
                duration = Double.doubleToLongBits(jsonObject.get("duration").isNumber().doubleValue());
                title = jsonObject.get("title").toString();
                description = jsonObject.get("description").toString();
            }
        }
    }

    private void saveDataToStorage(){
        if(localStorage != null){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("startTime", new JSONNumber(startTime));
            jsonObject.put("duration", new JSONNumber(duration));
            jsonObject.put("title", new JSONString(title));
            jsonObject.put("description", new JSONString(description));
            localStorage.setItem(tableNameInLocalStorage, jsonObject.toString());
        }
    }
}
