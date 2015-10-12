package com.tracker.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.core.client.GWT;
import com.mvp4g.client.Mvp4gModule;

public class TrackerEntryPoint implements EntryPoint {

    public void onModuleLoad() {
        Mvp4gModule module = GWT.create( Mvp4gModule.class );
        module.createAndStartModule();
        RootPanel.get().add( (Widget)module.getStartView() );
    }
}
