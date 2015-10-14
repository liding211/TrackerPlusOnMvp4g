package com.tracker.client.presenter;

import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.LazyPresenter;
import com.tracker.client.TrackerEventBus;
import com.tracker.client.presenter.interfaces.IAnalyticsView;
import com.tracker.client.presenter.interfaces.IAnalyticsView.IAnalyticsPresenter;
import com.tracker.client.view.AnalyticsView;

@Presenter(view = AnalyticsView.class)
public class AnalyticsPresenter extends LazyPresenter<IAnalyticsView, TrackerEventBus> implements IAnalyticsPresenter {

    public void onGoToAnalytics(){
        eventBus.setBody( view );
    }

}