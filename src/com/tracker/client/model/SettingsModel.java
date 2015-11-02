package com.tracker.client.model;

import com.google.gwt.json.client.*;
import com.google.gwt.storage.client.Storage;
import com.google.gwt.user.client.Window;

import java.util.HashMap;

public class SettingsModel {

    private String tableNameInLocalStorage;
    private LocaleDateTimeFormat currentDateTimeFormat;
    private HashMap availableDateTimeFormat;
    private Storage localStorage;
    private final String defaultLocaleName = "England";
    private static SettingsModel instance;

    public static synchronized SettingsModel getInstance(){
        if (instance == null){
            instance = new SettingsModel();
        }
        return instance;
    }

    private SettingsModel(){
        currentDateTimeFormat = null;
        tableNameInLocalStorage = "TrackerSettings";
        availableDateTimeFormat = new HashMap<String, LocaleDateTimeFormat>();
        localStorage = Storage.getLocalStorageIfSupported();

        LocaleDateTimeFormat EnglandDateTimeFormat = new LocaleDateTimeFormat();
        EnglandDateTimeFormat.setName("England");
        EnglandDateTimeFormat.setDateTimeFormatForDatepicker("dd/MM/yyyy hh:mm a");
        EnglandDateTimeFormat.setDateFormatForAnalytics("dd/MM/yyyy");
        EnglandDateTimeFormat.setDateTimeFormat("dd/mm/yyyy h:ii a");
        EnglandDateTimeFormat.setExample("18/09/2015 12:05");
        EnglandDateTimeFormat.setDateFormat("dd/mm/yyyy");
        EnglandDateTimeFormat.setTimeFormat("HH:mm:ss");
        EnglandDateTimeFormat.setLocalName("en");

        LocaleDateTimeFormat SwedenDateTimeFormat = new LocaleDateTimeFormat();
        SwedenDateTimeFormat.setName("Sweden");
        SwedenDateTimeFormat.setDateTimeFormatForDatepicker("yyyy-MM-dd HH.mm");
        SwedenDateTimeFormat.setDateFormatForAnalytics("yyyy-MM-dd");
        SwedenDateTimeFormat.setDateTimeFormat("yyyy-mm-dd hh.ii");
        SwedenDateTimeFormat.setExample("2015-09-18 15.59");
        SwedenDateTimeFormat.setDateFormat("yyyy-mm-dd");
        SwedenDateTimeFormat.setTimeFormat("HH.mm.ss");
        SwedenDateTimeFormat.setLocalName("se");

        availableDateTimeFormat.put("England", EnglandDateTimeFormat);
        availableDateTimeFormat.put("Sweden", SwedenDateTimeFormat);

        fetchDataFromStorage();
        if(currentDateTimeFormat == null) {
            setCurrentDateTimeFormat(defaultLocaleName);
        }
    };

    public void setCurrentDateTimeFormat(String locale){
        currentDateTimeFormat = (LocaleDateTimeFormat) availableDateTimeFormat.get(locale);
        saveDataToStorage();
    }

    public void fetchDataFromStorage(){
        if(localStorage != null){
            if(localStorage.getItem(tableNameInLocalStorage) != null) {
                JSONValue jsonValue = JSONParser.parse(localStorage.getItem(tableNameInLocalStorage));
                JSONObject jsonObject = jsonValue.isObject();

                currentDateTimeFormat = new LocaleDateTimeFormat();
                currentDateTimeFormat = (LocaleDateTimeFormat) availableDateTimeFormat.get(
                    jsonObject.get("localeDateTimeSettingsName").isString().stringValue()
                );
            }
        }
    }

    public void saveDataToStorage(){
        if(localStorage != null){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("localeDateTimeSettingsName", new JSONString(currentDateTimeFormat.getName()));
            localStorage.setItem(tableNameInLocalStorage, jsonObject.toString());
        }
    }

    public LocaleDateTimeFormat getCurrentDateTimeFormat(){
        return currentDateTimeFormat;
    }

    public HashMap<String, LocaleDateTimeFormat> getAvailableDateTimeFormat(){
        return availableDateTimeFormat;
    }
}
