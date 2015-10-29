package com.tracker.client.view;

import com.github.gwtbootstrap.client.ui.TextArea;
import com.github.gwtbootstrap.client.ui.TextBox;
import com.github.gwtbootstrap.datetimepicker.client.ui.DateTimeBoxAppended;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.TimeZone;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.SubmitButton;
import com.google.gwt.user.client.ui.Widget;
import com.tracker.client.model.LocaleDateTimeFormat;
import com.tracker.client.model.LogFormDataModel;
import com.tracker.client.model.SettingsModel;
import com.tracker.client.presenter.ILoggerPresenter;
import com.tracker.client.presenter.LoggerPresenter.ILoggerView;

import java.util.Date;

public class LoggerView extends ReverseCompositeView<ILoggerPresenter> implements ILoggerView {

    private static LoggerViewUiBinder uiBinder = GWT.create( LoggerViewUiBinder.class );

    interface LoggerViewUiBinder extends UiBinder<Widget, LoggerView> {}

    @UiField
    SubmitButton addTimeLog;

    @UiField
    DateTimeBoxAppended startDateTime;

    @UiField
    TextBox title;

    @UiField
    TextArea description;

    @UiField
    LoggerTimerView timer;

    private LogFormDataModel logFormData;
    private SettingsModel settings;

    @Override
    public void createView() {
        logFormData = LogFormDataModel.getInstance();
        settings = SettingsModel.getInstance();

        initWidget( uiBinder.createAndBindUi(this) );

        title.setValue(logFormData.getTitle());
        title.addBlurHandler( new BlurHandler() {
            @Override
            public void onBlur( BlurEvent event ) {
                logFormData.setTitle(title.getValue());
            }
        });

        description.setValue( logFormData.getDescription() );
        description.addBlurHandler( new BlurHandler() {
            @Override
            public void onBlur( BlurEvent event ) {
                logFormData.setDescription( description.getValue() );
            }
        });

        startDateTime.setFormat( settings.getCurrentDateTimeFormat().getDateTimeFormat() );
        startDateTime.setValue( new Date(logFormData.getStartTime()) );
    }

    @UiHandler( "reset" )
    public void onClickAnalytics( ClickEvent e ){ presenter.onResetFormData(); }
}
