package com.tracker.client.model;

public class LogFormDataModel {

    private Long startTime = new Long(0);
    private Long duration = new Long(0);
    private String title = "";
    private String description = "";

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

    public Long getDuration(){ return this.duration; }

    public String getTitle(){
        return this.title;
    }

    public String getDescription(){
        return this.description;
    }
}
