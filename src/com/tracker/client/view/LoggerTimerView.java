package com.tracker.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;

public class LoggerTimerView extends Composite {
    private Timer timerHandler;

    private static LoggerTimerViewUiBinder uiBinder = GWT.create(LoggerTimerViewUiBinder.class);

    @UiField
    Label timerText;

    @UiField
    HTMLPanel timerStartPanel;

    @UiField
    HTMLPanel timerControlPanel;

    interface LoggerTimerViewUiBinder extends UiBinder<Widget, LoggerTimerView> {}

    public LoggerTimerView(){
        timerStartPanel = new HTMLPanel("");
        timerControlPanel = new HTMLPanel("");
        timerText = new Label("00:00:00");

        initWidget( uiBinder.createAndBindUi(this) );

        timerStartPanel.addStyleName("input-group date");
        timerControlPanel.addStyleName("input-group form-group");
        timerStartPanel.setVisible(true);
        timerControlPanel.setVisible(false);
    }

    @UiHandler( "startTimer" )
    public void onStartTimer( ClickEvent e ){
        timerStartPanel.setVisible(false);
        timerControlPanel.setVisible(true);
        this.timerHandler = new Timer(){
            @Override
            public void run() {
                LoggerTimerView.this.setTimerText( 1 );
            }
        };
        this.timerHandler.scheduleRepeating(1000);
    }

    public void setTimerText( int diff ){
        timerText.setText( Integer.toString(diff) );
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
