package com.tracker.client.presenter;

import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.BasePresenter;
import com.tracker.client.TrackerEventBus;
import com.tracker.client.presenter.interfaces.IBodyView;
import com.tracker.client.presenter.interfaces.IBodyView.IBodyPresenter;
import com.tracker.client.view.BodyView;

@Presenter( view = BodyView.class )
public class BodyPresenter extends BasePresenter<IBodyView, TrackerEventBus> implements IBodyPresenter {

    public void onStart() {
        eventBus.setBody( view );
    }

}
