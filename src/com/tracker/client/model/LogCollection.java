package com.tracker.client.model;

import com.google.gwt.json.client.*;
import com.google.gwt.storage.client.Storage;

import java.util.ArrayList;
import java.util.List;

public class LogCollection {
    private ArrayList<LogModel> logCollection;
    private String tableNameInLocalStorage;
    private Storage localStorage;
    private static LogCollection instance;

    public static synchronized LogCollection getInstance(){
        if(instance == null){
            instance = new LogCollection();
        }
        return instance;
    }

    private LogCollection(){
        tableNameInLocalStorage = "TrackerLogsCollection";
        localStorage = Storage.getLocalStorageIfSupported();
        logCollection = new ArrayList();
    }

    public void fetchAllLogs(){
        if(localStorage != null){
            if(localStorage.getItem(tableNameInLocalStorage) != null) {
                logCollection.clear();
                JSONValue jsonValue = JSONParser.parse(localStorage.getItem(tableNameInLocalStorage));
                JSONObject jsonObject = jsonValue.isObject();

                for( String key : jsonObject.keySet()){
                    LogModel log = new LogModel();
                    String itemKey = log.getKeyWithPrefix( Long.decode(key) );
                    if(localStorage.getItem(itemKey) != null) {
                        JSONValue keyJsonValue = JSONParser.parse(localStorage.getItem(itemKey));
                        JSONObject keyJsonObject = keyJsonValue.isObject();
                        log.setTitle( keyJsonObject.get("title").isString().stringValue() );
                        log.setDescription( keyJsonObject.get("description").isString().stringValue() );
                        log.setDuration( Long.decode( keyJsonObject.get("duration").isNumber().toString() ) );
                        log.setStartTime( Long.decode( keyJsonObject.get("startTime").isNumber().toString() ) );
                        logCollection.add(log);
                    }
                }
            }
        }
    }

    public void addLogToCollection( Long key ){
        if(localStorage != null){
            JSONValue jsonValue;
            JSONObject jsonObject;

            if( localStorage.getItem(tableNameInLocalStorage) != null ){
                jsonValue = JSONParser.parse( localStorage.getItem(tableNameInLocalStorage) );
            } else {
                jsonValue = new JSONObject();
            }

            jsonObject = jsonValue.isObject() != null ? jsonValue.isObject() : new JSONObject();
            jsonObject.put( key.toString(), new JSONString(key.toString()));

            localStorage.setItem(tableNameInLocalStorage, jsonObject.toString());
        }
    }

    public void removeLogFromCollection( String key ){

    }

    public List<LogModel> getLogCollection(){
        fetchAllLogs();
        return logCollection;
    }
}
