package com.tracker.client.presenter.interfaces;

import com.google.gwt.user.client.ui.IsWidget;

public interface IMenuView extends IsWidget {

    public interface IMenuPresenter {

        void goToLogger();

        void goToAnalytics();

        void goToSettings();
    }

}
