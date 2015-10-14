package com.tracker.client.presenter;

import com.google.gwt.user.client.ui.IsWidget;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.BasePresenter;
import com.tracker.client.TrackerEventBus;
import com.tracker.client.presenter.IBodyPresenter;
import com.tracker.client.view.BodyView;

@Presenter( view = BodyView.class )
public class BodyPresenter extends BasePresenter<BodyPresenter.IBodyView, TrackerEventBus> implements IBodyPresenter {

    public interface IBodyView extends IsWidget {}

    public void onStart() {
        eventBus.setBody( view );
    }

}
