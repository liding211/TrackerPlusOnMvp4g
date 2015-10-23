package com.tracker.client.model;

public class LogFormDataModel {

    private Double startTime = new Double(0);
    private Double duration = new Double(0);
    private String title = "";
    private String description = "";

    public void setStartTime(Double startTime){
        this.startTime = startTime;
    }

    public void setDuration(Double duration){
        this.duration = duration;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public Double getStartTime(){
        return this.startTime;
    }

    public Double getDuration(){
        return this.duration;
    }

    public String getTitle(){
        return this.title;
    }

    public String getDescription(){
        return this.description;
    }
}
