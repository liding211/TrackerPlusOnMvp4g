package com.tracker.client.presenter;

import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.core.client.Duration;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.LazyPresenter;
import com.mvp4g.client.view.LazyView;
import com.tracker.client.TrackerEventBus;
import com.tracker.client.model.LogFormDataModel;
import com.tracker.client.model.LogModel;
import com.tracker.client.view.LoggerTimerView;
import com.tracker.client.view.LoggerView;

import java.util.Date;


@Presenter(view = LoggerView.class)
public class LoggerPresenter extends LazyPresenter<LoggerPresenter.ILoggerView, TrackerEventBus> implements ILoggerPresenter {

    @UiField
    LoggerTimerView loggerTimer;

    public interface ILoggerView extends IsWidget, LazyView {}
    private LogFormDataModel logFormData = new LogFormDataModel();

//    @Override
//    public void createPresenter() {
//        Element startButton = DOM.getElementById("tracker-start-button");
//
//        Label.wrap(startButton).addClickHandler(new ClickHandler() {
//            @Override
//            public void onClick(ClickEvent event) {
//                eventBus.startTimer();
//            }
//        });
//    }

    public void onGoToLogger(){
        eventBus.setBody( view );
    }

    public void onStartTimer(){
        this.logFormData.setStartTime( Duration.currentTimeMillis() );
        eventBus.setBody( view );
    };

    public void onStopTimer(){
        Window.alert("onStopTimer");
        eventBus.setBody( view );
    };

    public void onPauseTimer(){
        Window.alert("onPauseTimer");
        eventBus.setBody( view );
    };

    public void onAddLog(){
        Window.alert("onAddLog");
        eventBus.setBody( view );
    };

    public void onResetFormData(){
        Window.alert("onResetFormData");
        eventBus.setBody( view );
    };
}