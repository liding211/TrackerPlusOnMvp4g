package com.tracker.client;

import com.mvp4g.client.annotation.History;
import com.mvp4g.client.annotation.History.HistoryConverterType;
import com.mvp4g.client.history.HistoryConverter;

@History( type = HistoryConverterType.SIMPLE )
public class TrackerHistoryConverter implements HistoryConverter<TrackerEventBus> {
    @Override
    public boolean isCrawlable() {
        return false;
    }

    @Override
    public void convertFromToken( String name, String params, TrackerEventBus trackerEventBus) {
        trackerEventBus.dispatch( name, params );
    }

    public String convertToToken( String eventName ) {
        return convertToToken( eventName, "" );
    }

    public String convertToToken( String eventName, String params ) {
        return params;
    }
}