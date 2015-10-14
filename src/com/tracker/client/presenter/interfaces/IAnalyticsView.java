package com.tracker.client.presenter.interfaces;

import com.google.gwt.user.client.ui.IsWidget;
import com.mvp4g.client.view.LazyView;

public interface IAnalyticsView extends IsWidget, LazyView {

    public interface IAnalyticsPresenter {

    }

    public void onGoToAnalytics();
}
