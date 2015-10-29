package com.tracker.client.view;

import com.github.gwtbootstrap.client.ui.*;
import com.github.gwtbootstrap.client.ui.Button;
import com.github.gwtbootstrap.client.ui.TextBox;
import com.github.gwtbootstrap.client.ui.constants.IconType;
import com.github.gwtbootstrap.datetimepicker.client.ui.DateTimeBox;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.*;
import com.tracker.client.model.LogFormDataModel;
import com.google.gwt.i18n.client.TimeZone;

import java.util.Date;

public class LoggerTimerView extends Composite {

    private static LoggerTimerViewUiBinder uiBinder = GWT.create(LoggerTimerViewUiBinder.class);

    interface LoggerTimerViewUiBinder extends UiBinder<Widget, LoggerTimerView> {}

    @UiField
    TextBox timerText;

    @UiField
    AppendButton timerStartPanel;

    @UiField
    HTMLPanel timerControlPanel;

    @UiField
    Button pauseTimer;

    @UiField
    DateTimeBox timer;

    private Timer timerHandler;
    private LogFormDataModel logFormData;

    public LoggerTimerView(){
        logFormData = LogFormDataModel.getInstance();

        initWidget(uiBinder.createAndBindUi(this));

        timerControlPanel.addStyleName("input-append input-prepend");
        timerControlPanel.setVisible(false);
        timerStartPanel.setVisible(true);

        setTimerValue(logFormData.getDuration());
    }

    public void setTimerTextValue( long diff ){
        Date date = new Date(diff);
        TimeZone tz = TimeZone.createTimeZone(0);
        String formatter = DateTimeFormat.getFormat("HH:mm:ss").format(date, tz);
        timerText.setText( formatter );
    }

    public void setTimerValue( long timeStamp ){
        timer.setValue( new Date( timeStamp + getTimeZoneOffsetInMillis() ) );
    }

    public native int getTimeZoneOffsetInMillis() /*-{
        var x = new Date();
        var currentTimeZoneOffsetInMillis = x.getTimezoneOffset() * 60 * 1000;
        return currentTimeZoneOffsetInMillis;
    }-*/;

    @UiHandler( "startTimer" )
    public void onStartTimer( ClickEvent e ){
        timerStartPanel.setVisible(false);
        timerControlPanel.setVisible(true);

        logFormData.setDuration( new Long(0) );
        logFormData.setStartTime(System.currentTimeMillis());

        setTimerValue(logFormData.getStartTime());

        this.timerHandler = new Timer(){
            @Override
            public void run() {
                LoggerTimerView.this.logFormData.setDuration(LoggerTimerView.this.logFormData.getDuration() + 1000 );
                int timeDuration = LoggerTimerView.this.logFormData.getDuration().intValue();
                LoggerTimerView.this.setTimerTextValue(timeDuration);
            }
        };
        timerHandler.scheduleRepeating(1000);
        setTimerTextValue(0);
    }

    @UiHandler("stopTimer")
    public void onStopTimer( ClickEvent e ){
        logFormData.saveDataToStorage();
        setTimerValue(logFormData.getDuration());

        timerStartPanel.setVisible(true);
        timerControlPanel.setVisible(false);

        pauseTimer.setIcon(IconType.PAUSE);
        timerHandler.cancel();
    }

    @UiHandler( "pauseTimer" )
    public void onPauseTimer( ClickEvent e ){
        if(timerHandler.isRunning()) {
            pauseTimer.setIcon(IconType.PLAY);
            timerHandler.cancel();
        } else {
            pauseTimer.setIcon(IconType.PAUSE);
            timerHandler.scheduleRepeating(1000);
        }
    }
}
