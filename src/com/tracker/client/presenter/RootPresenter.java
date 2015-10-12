package com.tracker.client.presenter;

import com.google.gwt.user.client.ui.IsWidget;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.BasePresenter;
import com.tracker.client.TrackerEventBus;
import com.tracker.client.presenter.interfaces.IRootView;
import com.tracker.client.presenter.interfaces.IRootView.IRootPresenter;
import com.tracker.client.view.RootView;

@Presenter( view = RootView.class )
public class RootPresenter extends BasePresenter<IRootView, TrackerEventBus> implements IRootPresenter {

    public void onStart() { eventBus.goToLogger( "The application started." ); }

    public void onSetMenu( IsWidget menu ) {
        view.setMenu( menu );
    }

    public void onSetBody( IsWidget body ) {
        view.setBody( body );
    }

}
