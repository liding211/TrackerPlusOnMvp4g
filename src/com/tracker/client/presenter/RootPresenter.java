package com.tracker.client.presenter;

import com.google.gwt.user.client.ui.IsWidget;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.BasePresenter;
import com.tracker.client.TrackerEventBus;
import com.tracker.client.presenter.IRootPresenter;
import com.tracker.client.view.RootView;

@Presenter( view = RootView.class )
public class RootPresenter extends BasePresenter<RootPresenter.IRootView, TrackerEventBus> implements IRootPresenter {

    public interface IRootView extends IsWidget {

        void setBody( IsWidget body );

        void setMenu( IsWidget menu );

    }

    public void onInit() {
        eventBus.goToLogger();
    }

    public void onSetBody( IsWidget body ) {
        view.setBody( body );
    }

    public void onSetMenu( IsWidget menu ) { view.setMenu( menu ); }
}
