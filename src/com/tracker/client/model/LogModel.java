package com.tracker.client.model;

public class LogModel {

    private int startTime = 0;
    private int duration = 0;
    private String title = "";
    private String description = "";

    public LogModel(int startTime, int duration, String title) {
        this.startTime = startTime;
        this.duration = duration;
        this.title = title;
    }

    public LogModel(int startTime, int duration, String title, String description) {
        this.startTime = startTime;
        this.duration = duration;
        this.title = title;
        this.description = description;
    }

    public void setStartTime(int startTime){
        this.startTime = startTime;
    }

    public void setDuration(int duration){
        this.duration = duration;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public int getStartTime(){
        return this.startTime;
    }

    public int getDuration(){
        return this.duration;
    }

    public String getTitle(){
        return this.title;
    }

    public String getDescription(){
        return this.description;
    }
}
