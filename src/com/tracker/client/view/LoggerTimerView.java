package com.tracker.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.SpanElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.DOM;
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

    @UiField
    Anchor pauseTimer;

    @UiField
    SpanElement pauseButtonIcon;

    interface LoggerTimerViewUiBinder extends UiBinder<Widget, LoggerTimerView> {}

    private Timer timerHandler;
    private LogFormDataModel logFormData = LogFormDataModel.getInstance();

    public LoggerTimerView(){
        timerStartPanel = new HTMLPanel("");
        timerControlPanel = new HTMLPanel("");
        timerText = new Label();

        initWidget(uiBinder.createAndBindUi(this) );

        timerStartPanel.addStyleName("input-group date");
        timerStartPanel.setVisible(true);

        timerControlPanel.addStyleName("input-group form-group");
        timerControlPanel.setVisible(false);
    }

    @UiHandler( "startTimer" )
    public void onStartTimer( ClickEvent e ){
        timerStartPanel.setVisible(false);
        timerControlPanel.setVisible(true);

        LoggerTimerView.this.logFormData.setDuration( new Long(0) );
        logFormData.setStartTime(System.currentTimeMillis());

        this.timerHandler = new Timer(){
            @Override
            public void run() {
                LoggerTimerView.this.logFormData.setDuration(LoggerTimerView.this.logFormData.getDuration() + 1000 );
                int timeDuration = LoggerTimerView.this.logFormData.getDuration().intValue();
                LoggerTimerView.this.setTimerValue( timeDuration );
            }
        };
        timerHandler.scheduleRepeating(1000);
        setTimerValue(0);
    }

    public void setTimerValue( int diff ){
        Date date = new Date(diff);
        TimeZone tz = TimeZone.createTimeZone(0);
        String formatter = DateTimeFormat.getFormat("HH:mm:ss").format(date, tz);
        timerText.setText( formatter );
    }

    @UiHandler( "stopTimer" )
    public void onStopTimer( ClickEvent e ){
        timerStartPanel.setVisible(true);
        timerControlPanel.setVisible(false);
        pauseButtonIcon.setClassName("glyphicon glyphicon-pause");
        timerHandler.cancel();
    }

    @UiHandler( "pauseTimer" )
    public void onPauseTimer( ClickEvent e ){
        if(timerHandler.isRunning()) {
            pauseButtonIcon.setClassName("glyphicon glyphicon-play");
            timerHandler.cancel();
        } else {
            pauseButtonIcon.setClassName("glyphicon glyphicon-pause");
            timerHandler.scheduleRepeating(1000);
        }
    }
}
