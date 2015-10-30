package com.tracker.client;

import com.google.gwt.cell.client.DateCell;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.core.client.GWT;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;
import com.mvp4g.client.Mvp4gModule;
import com.tracker.client.model.LogCollection;
import com.tracker.client.model.LogModel;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class TrackerEntryPoint implements EntryPoint {

    public void onModuleLoad() {
        Mvp4gModule module = GWT.create( Mvp4gModule.class );
        module.createAndStartModule();
        RootPanel.get().add( (Widget)module.getStartView() );
    }
}
