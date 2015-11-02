package com.tracker.client.model;

public class LocaleDateTimeFormat {
    private String localName;
    private String name;
    private String dateTimeFormat;
    private String dateFormat;
    private String timeFormat;
    private String example;
    private String dateTimeFormatForDatepicker;
    private String dateFormatForAnalytics;

    public void setLocalName(String localName) { this.localName = localName; };

    public void setName(String name) { this.name = name; };

    public void setDateTimeFormat(String dateTimeFormat) { this.dateTimeFormat = dateTimeFormat; };

    public void setDateTimeFormatForDatepicker(String dateTimeFormatForDatepicker) { this.dateTimeFormatForDatepicker = dateTimeFormatForDatepicker; };

    public void setDateFormatForAnalytics(String dateFormatForAnalytics) { this.dateFormatForAnalytics = dateFormatForAnalytics; };

    public void setDateFormat(String dateFormat) { this.dateFormat = dateFormat; };

    public void setTimeFormat(String timeFormat) { this.timeFormat = timeFormat; };

    public void setExample(String example) { this.example = example; };

    public String getLocalName() { return this.localName; };

    public String getName() { return this.name; };

    public String getDateTimeFormat() { return this.dateTimeFormat; };

    public String getDateTimeFormatForDatepicker() { return this.dateTimeFormatForDatepicker; };

    public String getDateFormatForAnalytics() { return this.dateFormatForAnalytics; };

    public String getDateFormat() { return this.dateFormat; };

    public String getTimeFormat() { return this.timeFormat; };

    public String getExample() { return this.example; };

}
