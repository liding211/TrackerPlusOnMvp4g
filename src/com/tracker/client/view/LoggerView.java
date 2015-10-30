package com.tracker.client.view;

import com.github.gwtbootstrap.client.ui.*;
import com.github.gwtbootstrap.client.ui.Button;
import com.github.gwtbootstrap.client.ui.TextArea;
import com.github.gwtbootstrap.client.ui.TextBox;
import com.github.gwtbootstrap.client.ui.constants.IconType;
import com.github.gwtbootstrap.datetimepicker.client.ui.DateTimeBox;
import com.github.gwtbootstrap.datetimepicker.client.ui.DateTimeBoxAppended;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.*;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.TimeZone;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.cellview.client.*;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.user.client.ui.SubmitButton;
import com.tracker.client.model.LogCollection;
import com.tracker.client.model.LogFormDataModel;
import com.tracker.client.model.LogModel;
import com.tracker.client.model.SettingsModel;
import com.tracker.client.presenter.ILoggerPresenter;
import com.tracker.client.presenter.LoggerPresenter.ILoggerView;

import java.util.Date;
import java.util.List;

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
    TextBox timerText;

    @UiField
    AppendButton timerStartPanel;

    @UiField
    HTMLPanel timerControlPanel;

    @UiField
    Button pauseTimer;

    @UiField
    DateTimeBox timer;

    @UiField
    Form logForm;

    @UiField
    VerticalPanel logsTableHandler;

    private Timer timerHandler;
    private LogFormDataModel logFormData;
    private LogCollection logCollection;
    private SettingsModel settings;

    @Override
    public void createView() {
        logFormData = LogFormDataModel.getInstance();
        settings = SettingsModel.getInstance();
        logCollection = LogCollection.getInstance();

        initWidget( uiBinder.createAndBindUi(this) );

        timerControlPanel.addStyleName("input-append input-prepend");
        timerControlPanel.setVisible(false);
        timerStartPanel.setVisible(true);

        setTimerValue(logFormData.getDuration());
        setLogsTable();

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
        startDateTime.setValue( new Date( logFormData.getStartTime() ) );
        startDateTime.addChangeHandler(new ChangeHandler() {
            @Override
            public void onChange(ChangeEvent event) {
                logFormData.setStartTime( startDateTime.getValue().getTime() );
            }
        });
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

    public void setLogsTable(){
        logsTableHandler.clear();
        List<LogModel> LOGS = LogCollection.getInstance().getLogCollection();

        CellTable<LogModel> table = new CellTable<LogModel>();
        table.setKeyboardSelectionPolicy(HasKeyboardSelectionPolicy.KeyboardSelectionPolicy.ENABLED);

        TextColumn<LogModel> startDateTimeColumn = new TextColumn<LogModel>() {
            @Override
            public String getValue(LogModel object) {
                Date date = new Date( object.getStartTime() );
                TimeZone tz = TimeZone.createTimeZone(0);
                String formatter = DateTimeFormat.getFormat(  settings.getCurrentDateTimeFormat().getDateTimeFormatForDatepicker() ).format(date, tz);
                return formatter;
            }
        };

        TextColumn<LogModel> duratiornColumn = new TextColumn<LogModel>() {
            @Override
            public String getValue(LogModel object) {
                Date date = new Date( object.getDuration() );
                TimeZone tz = TimeZone.createTimeZone(0);
                String formatter = DateTimeFormat.getFormat(  settings.getCurrentDateTimeFormat().getTimeFormat() ).format(date, tz);
                return formatter;
            }
        };

        TextColumn<LogModel> titleColumn = new TextColumn<LogModel>() {
            @Override
            public String getValue(LogModel object) {
                return object.getTitle();
            }
        };

        TextColumn<LogModel> descriptionColumn = new TextColumn<LogModel>() {
            @Override
            public String getValue(LogModel object) {
                return object.getDescription();
            }
        };

        table.addColumn(startDateTimeColumn, "Start time");
        table.addColumn(titleColumn, "Title");
        table.addColumn(duratiornColumn, "Time spent");
        table.addColumn(descriptionColumn, "Address");

        table.setColumnWidth(startDateTimeColumn, 25, Style.Unit.PCT);
        table.setColumnWidth(titleColumn, 25, Style.Unit.PCT);
        table.setColumnWidth(duratiornColumn, 25, Style.Unit.PCT);
        table.setColumnWidth(descriptionColumn, 25, Style.Unit.PCT);

        // Add a selection model to handle user selection.
//        final SingleSelectionModel<LogModel> selectionModel
//                = new SingleSelectionModel<LogModel>();
//        table.setSelectionModel(selectionModel);
//        selectionModel.addSelectionChangeHandler(
//            new SelectionChangeEvent.Handler() {
//                public void onSelectionChange(SelectionChangeEvent event) {
//                    LogModel selected = selectionModel.getSelectedObject();
//                    if (selected != null) {
//                        Window.alert("You selected: " + selected.getTitle());
//                    }
//                }
//            }
//        );

        table.setRowCount(LOGS.size(), true);

        table.setRowData(0, LOGS);

        logsTableHandler.setWidth("100%");
        logsTableHandler.add(table);
    }

    public void displayNewLog( LogModel log ){
        HTML panel = new HTML();
    }

    public void clearForm(){
        startDateTime.setValue(new Date());
        title.setValue("");
        setTimerValue(0);
        description.setValue("");
    }

    @UiHandler("addTimeLog")
    public void onAddTimeLog( ClickEvent e ){
//        filterFormData();

        LogModel newLog = new LogModel(
            logFormData.getStartTime(),
            logFormData.getDuration(),
            title.getValue(),
            description.getValue()
        );

        displayNewLog(newLog);
        logFormData.removeDataFromStorage();
        clearForm();
        setLogsTable();
    }

    @UiHandler( "startTimer" )
    public void onStartTimer( ClickEvent e ){
        timerStartPanel.setVisible(false);
        timerControlPanel.setVisible(true);

        logFormData.setDuration( new Long(0) );
        logFormData.setStartTime( System.currentTimeMillis() );

        setTimerValue( logFormData.getStartTime() );
        startDateTime.setValue( new Date( logFormData.getStartTime() ) );

        this.timerHandler = new Timer(){
            @Override
            public void run() {
                LoggerView.this.logFormData.setDuration( LoggerView.this.logFormData.getDuration() + 1000 );
                int timeDuration = LoggerView.this.logFormData.getDuration().intValue();
                LoggerView.this.setTimerTextValue(timeDuration);
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

    @UiHandler( "reset" )
    public void onClickAnalytics( ClickEvent e ){
        logFormData.removeDataFromStorage();
        clearForm();
    }
}
