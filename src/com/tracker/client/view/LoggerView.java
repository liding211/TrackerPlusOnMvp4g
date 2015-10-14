package com.tracker.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.tracker.client.presenter.ILoggerPresenter;
import com.tracker.client.presenter.LoggerPresenter.ILoggerView;

public class LoggerView extends ReverseCompositeView<ILoggerPresenter> implements ILoggerView {

    private static LoggerViewUiBinder uiBinder = GWT.create( LoggerViewUiBinder.class );

    interface LoggerViewUiBinder extends UiBinder<Widget, LoggerView> {
    }

//    @UiField
//    Label text;

    @Override
    public void createView() {
        //don't create the view before to take advantage of the lazy loading mechanism
        initWidget( uiBinder.createAndBindUi( this ) );
    }

    //@Override
    //public void setName( String name ) {
    //    text.setText( "Welcome to page 1. " + name );
    //}

}
