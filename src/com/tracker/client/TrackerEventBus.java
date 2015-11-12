package com.tracker.client;

import com.google.gwt.user.client.ui.IsWidget;
import com.mvp4g.client.annotation.*;
import com.mvp4g.client.event.EventBusWithLookup;
import com.tracker.client.presenter.MenuPresenter;
import com.tracker.client.presenter.LoggerPresenter;
import com.tracker.client.presenter.AnalyticsPresenter;
import com.tracker.client.presenter.SettingsPresenter;
import com.tracker.client.presenter.RootPresenter;

@Events( startPresenter = RootPresenter.class, historyOnStart = true )
public interface TrackerEventBus extends EventBusWithLookup {

    @Start
    @Event( handlers = MenuPresenter.class )
    void start();

    @InitHistory
    @Event( handlers = RootPresenter.class )
    void init();

    /*
     * Layout events
     */
    @Event( handlers = RootPresenter.class )
    void setBody( IsWidget body );

    @Event( handlers = RootPresenter.class )
    void setMenu( IsWidget menu );

    /*
     * Logger events
     */
    @Event( handlers = {LoggerPresenter.class, MenuPresenter.class}, historyConverter = TrackerHistoryConverter.class, name = "tracker" )
    String goToLogger();

    /*
     * Analytics events
     */
    @Event( handlers = {AnalyticsPresenter.class, MenuPresenter.class}, historyConverter = TrackerHistoryConverter.class, name = "analytics" )
    String goToAnalytics( String params );

    /*
     * Settings events
     */
    @Event( handlers = {SettingsPresenter.class, MenuPresenter.class}, historyConverter = TrackerHistoryConverter.class, name = "settings" )
    String goToSettings();
}
