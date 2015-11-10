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
    public void convertFromToken( String name, String param, TrackerEventBus trackerEventBus) {
        trackerEventBus.dispatch( name, param );
    }

    public String convertToToken( String eventName ) {
        return eventName;
    }
}