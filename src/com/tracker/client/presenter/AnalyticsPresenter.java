package com.tracker.client.presenter;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.IsWidget;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.LazyPresenter;
import com.mvp4g.client.view.LazyView;
import com.tracker.client.TrackerEventBus;
import com.tracker.client.presenter.IAnalyticsPresenter;
import com.tracker.client.view.AnalyticsView;

@Presenter(view = AnalyticsView.class)
public class AnalyticsPresenter extends LazyPresenter<AnalyticsPresenter.IAnalyticsView, TrackerEventBus> implements IAnalyticsPresenter {

    public interface IAnalyticsView extends IsWidget, LazyView {
        public void resetView();
    }

    public void onGoToAnalytics(){
        view.resetView();
        eventBus.setBody( view );
    }
}