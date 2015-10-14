package com.tracker.client.presenter;

import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.LazyPresenter;
import com.tracker.client.TrackerEventBus;
import com.tracker.client.presenter.interfaces.ILoggerView;
import com.tracker.client.presenter.interfaces.ILoggerView.ILoggerPresenter;
import com.tracker.client.view.LoggerView;

@Presenter(view = LoggerView.class)
public class LoggerPresenter extends LazyPresenter<ILoggerView, TrackerEventBus> implements ILoggerPresenter {

    public void onGoToLogger(){
        //view.setName( name );
        eventBus.setBody( view );
    }

}