package com.tracker.client.presenter;

import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.LazyPresenter;
import com.tracker.client.TrackerEventBus;
import com.tracker.client.presenter.interfaces.ISettingsView;
import com.tracker.client.presenter.interfaces.ISettingsView.ISettingsPresenter;
import com.tracker.client.view.SettingsView;

@Presenter(view = SettingsView.class)
public class SettingsPresenter extends LazyPresenter<ISettingsView, TrackerEventBus> implements ISettingsPresenter {

    public void onGoToSettings(String name){
        //view.setName( name );
        eventBus.setBody( view );
    }

}