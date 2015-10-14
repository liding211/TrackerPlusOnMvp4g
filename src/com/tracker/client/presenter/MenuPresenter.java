package com.tracker.client.presenter;

import com.google.gwt.user.client.Window;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.BasePresenter;
import com.tracker.client.TrackerEventBus;
import com.tracker.client.presenter.interfaces.IMenuView;
import com.tracker.client.presenter.interfaces.IMenuView.IMenuPresenter;
import com.tracker.client.view.MenuView;

@Presenter( view = MenuView.class )
public class MenuPresenter extends BasePresenter<IMenuView, TrackerEventBus> implements IMenuPresenter {

    public void onStart() {
        eventBus.setMenu( view );
    }

    @Override
    public void goToLogger() {
        eventBus.goToLogger( "You clicked the menu 'logger'." );
    }

    @Override
    public void goToAnalytics() { eventBus.goToAnalytics(); }

    @Override
    public void goToSettings() {
        eventBus.goToSettings();
    }
}
