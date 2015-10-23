package com.tracker.client.view;

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

    @UiField
    Label timerText;

    @UiField
    HTMLPanel timerStartPanel;

    @UiField
    HTMLPanel timerControlPanel;

    interface LoggerTimerViewUiBinder extends UiBinder<Widget, LoggerTimerView> {}

    private Timer timerHandler;
    private LogFormDataModel logFormData = new LogFormDataModel();

    public LoggerTimerView(){
        timerStartPanel = new HTMLPanel("");
        timerControlPanel = new HTMLPanel("");
        timerText = new Label();

        initWidget(uiBinder.createAndBindUi(this) );

        timerStartPanel.addStyleName("input-group date");
        timerControlPanel.addStyleName("input-group form-group");
        timerStartPanel.setVisible(true);
        timerControlPanel.setVisible(false);
    }

    @UiHandler( "startTimer" )
    public void onStartTimer( ClickEvent e ){
        timerStartPanel.setVisible(false);
        timerControlPanel.setVisible(true);

        logFormData.setStartTime( System.currentTimeMillis() );

        this.timerHandler = new Timer(){
            @Override
            public void run() {
                int timeDuration = (int) ( System.currentTimeMillis() - LoggerTimerView.this.logFormData.getStartTime());
                LoggerTimerView.this.setTimerValue( timeDuration );
            }
        };
        this.timerHandler.scheduleRepeating(1000);
        timerText.setText("00:00:00");
    }

    public void setTimerValue( int diff ){
        Date date = new Date(diff);
        TimeZone tz = TimeZone.createTimeZone(0);
        //date.setTime( Math.round(diff / 1000) );
        String formatter = DateTimeFormat.getFormat("HH:mm:ss").format(date, tz);
        timerText.setText( formatter );
    }

    @UiHandler( "stopTimer" )
    public void onStopTimer( ClickEvent e ){
        timerStartPanel.setVisible(true);
        timerControlPanel.setVisible(false);
        this.timerHandler.cancel();
    }

    public void onPauseTimer(){
        this.timerHandler.cancel();
    }
}
