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
        EnglandDateTimeFormat.setDateFormat("dd/mm/yyyy");
        EnglandDateTimeFormat.setDateFormatForAnalytics("dd/MM/yyyy");
        EnglandDateTimeFormat.setDatepickerDateTimeFormat("dd/mm/yyyy hh:ii");
        EnglandDateTimeFormat.setDatepickerTimeFormat("HH:ii:ss");
        EnglandDateTimeFormat.setDateTimeFormat("dd/MM/yyyy hh:mm a");
        EnglandDateTimeFormat.setExample("18/09/2015 12:05");
        EnglandDateTimeFormat.setLocalName("en");
        EnglandDateTimeFormat.setName("England");
        EnglandDateTimeFormat.setTimeFormat("HH:mm:ss");

        LocaleDateTimeFormat SwedenDateTimeFormat = new LocaleDateTimeFormat();
        SwedenDateTimeFormat.setDateFormat("yyyy-mm-dd");
        SwedenDateTimeFormat.setDateFormatForAnalytics("yyyy-MM-dd");
        SwedenDateTimeFormat.setDatepickerDateTimeFormat("yyyy-mm-dd hh.ii");
        SwedenDateTimeFormat.setDatepickerTimeFormat("HH.ii.ss");
        SwedenDateTimeFormat.setDateTimeFormat("yyyy-MM-dd HH.mm");
        SwedenDateTimeFormat.setExample("2015-09-18 15.59");
        SwedenDateTimeFormat.setLocalName("se");
        SwedenDateTimeFormat.setName("Sweden");
        SwedenDateTimeFormat.setTimeFormat("HH.mm.ss");

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
