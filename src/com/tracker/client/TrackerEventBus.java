package com.tracker.client;

import com.google.gwt.user.client.ui.IsWidget;
import com.mvp4g.client.annotation.Event;
import com.mvp4g.client.annotation.Events;
import com.mvp4g.client.annotation.InitHistory;
import com.mvp4g.client.annotation.Start;
import com.mvp4g.client.event.EventBus;
import com.tracker.client.presenter.MenuPresenter;
import com.tracker.client.presenter.LoggerPresenter;
import com.tracker.client.presenter.AnalyticsPresenter;
import com.tracker.client.presenter.SettingsPresenter;
import com.tracker.client.presenter.RootPresenter;

@Events( startPresenter = RootPresenter.class )
public interface TrackerEventBus extends EventBus {

    @Start
    @Event( handlers = { MenuPresenter.class, RootPresenter.class } )
    void start();

    /*
     * Layout events
     */
    @Event( handlers = RootPresenter.class )
    void setBody( IsWidget body );

    @Event( handlers = RootPresenter.class )
    void setMenu( IsWidget menu );

    /*
     * Place events
     */
    @Event( handlers = LoggerPresenter.class )
    void goToLogger();

    @Event( handlers = AnalyticsPresenter.class )
    void goToAnalytics();

    @Event( handlers = SettingsPresenter.class )
    void goToSettings();
}
