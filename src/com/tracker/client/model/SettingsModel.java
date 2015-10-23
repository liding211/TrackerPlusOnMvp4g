package com.tracker.client.model;

import java.util.HashMap;

public class SettingsModel {
    private LocaleDateTimeFormat currentDateTimeFormat = new LocaleDateTimeFormat();

    private HashMap availableDateTimeFormat = new HashMap<String, LocaleDateTimeFormat>();

    SettingsModel(){
        LocaleDateTimeFormat EnglandDateTimeFormat = new LocaleDateTimeFormat();
        EnglandDateTimeFormat.setName("England");
        EnglandDateTimeFormat.setDateTimeFormat("DD/MM/YYYY hh:mm A");
        EnglandDateTimeFormat.setExample("18/09/2015 12:05 PM");
        EnglandDateTimeFormat.setDateFormat("DD/MM/YYYY");
        EnglandDateTimeFormat.setTimeFormat("HH:mm:ss");
        EnglandDateTimeFormat.setLocalName("en");

        LocaleDateTimeFormat SwedenDateTimeFormat = new LocaleDateTimeFormat();
        SwedenDateTimeFormat.setName("Sweden");
        SwedenDateTimeFormat.setDateTimeFormat("YYYY-MM-DD HH.mm");
        SwedenDateTimeFormat.setExample("2015-09-18 15.59");
        SwedenDateTimeFormat.setDateFormat("YYYY-MM-DD");
        SwedenDateTimeFormat.setTimeFormat("HH:mm:ss");
        SwedenDateTimeFormat.setLocalName("se");

        this.availableDateTimeFormat.put("England", EnglandDateTimeFormat);
        this.availableDateTimeFormat.put("Sweden", EnglandDateTimeFormat);

        this.currentDateTimeFormat = EnglandDateTimeFormat;
    };
}
