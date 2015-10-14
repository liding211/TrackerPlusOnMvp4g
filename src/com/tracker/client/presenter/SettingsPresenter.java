package com.tracker.client.presenter;

import com.google.gwt.user.client.ui.IsWidget;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.LazyPresenter;
import com.mvp4g.client.view.LazyView;
import com.tracker.client.TrackerEventBus;
import com.tracker.client.presenter.ISettingsPresenter;
import com.tracker.client.view.SettingsView;

@Presenter(view = SettingsView.class)
public class SettingsPresenter extends LazyPresenter<SettingsPresenter.ISettingsView, TrackerEventBus> implements ISettingsPresenter {

    public interface ISettingsView extends IsWidget, LazyView {}

    public void onGoToSettings(){
        eventBus.setBody( view );
    }

}