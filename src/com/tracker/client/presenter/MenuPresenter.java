package com.tracker.client.presenter;

import com.google.gwt.user.client.ui.IsWidget;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.BasePresenter;
import com.tracker.client.TrackerEventBus;
import com.tracker.client.view.MenuView;

@Presenter( view = MenuView.class )
public class MenuPresenter extends BasePresenter<MenuPresenter.IMenuView, TrackerEventBus> implements IMenuPresenter {

    public interface IMenuView extends IsWidget {
        public void reset();
    }

    public void onStart() {
        eventBus.setMenu( view );
    }

    @Override
    public void goToLogger() {
        eventBus.goToLogger();
    }

    @Override
    public void goToAnalytics() { eventBus.goToAnalytics( "" ); }

    @Override
    public void goToSettings() { eventBus.goToSettings(); }

    public void onGoToLogger() { resetView(); }

    public void onGoToAnalytics( String params ) { resetView(); }

    public void onGoToSettings() { resetView(); }

    public void resetView(){ view.reset(); }

}
