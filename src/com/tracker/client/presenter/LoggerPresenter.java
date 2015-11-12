package com.tracker.client.presenter;

import com.google.gwt.user.client.ui.IsWidget;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.LazyPresenter;
import com.mvp4g.client.view.LazyView;
import com.tracker.client.TrackerEventBus;
import com.tracker.client.view.LoggerView;

@Presenter(view = LoggerView.class)
public class LoggerPresenter extends LazyPresenter<LoggerPresenter.ILoggerView, TrackerEventBus> implements ILoggerPresenter {

    public interface ILoggerView extends IsWidget, LazyView {
        public void resetView();
    }

    public void onGoToLogger(){
        view.resetView();
        eventBus.setBody( view );
    }
}