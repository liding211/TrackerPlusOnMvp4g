package com.tracker.client.view;

import com.github.gwtbootstrap.client.ui.*;
import com.github.gwtbootstrap.client.ui.Button;
import com.github.gwtbootstrap.client.ui.TextArea;
import com.github.gwtbootstrap.client.ui.TextBox;
import com.github.gwtbootstrap.client.ui.constants.AlternateSize;
import com.github.gwtbootstrap.client.ui.constants.IconType;
import com.github.gwtbootstrap.datetimepicker.client.ui.DateTimeBox;
import com.github.gwtbootstrap.datetimepicker.client.ui.DateTimeBoxAppended;
import com.github.gwtbootstrap.datetimepicker.client.ui.base.HasViewMode;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.*;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.TimeZone;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.cellview.client.*;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
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
    HTMLPanel startDateTimePlaceholder;

    @UiField
    TextBox title;

    @UiField
    TextArea description;

    @UiField
    TextBox textTimer;

    @UiField
    AppendButton timerStartPanel;

    @UiField
    HTMLPanel timerControlPanel;

    @UiField
    HTMLPanel startDateBox;

    @UiField
    HTMLPanel titleBox;

    @UiField
    HTMLPanel timerBox;

    @UiField
    Button pauseTimer;

    @UiField
    Form logForm;

    @UiField
    VerticalPanel logsTableHandler;

    @UiField
    HelpBlock startDateTimeErrorBlock;

    @UiField
    HelpBlock titleErrorBlock;

    @UiField
    HelpBlock timerErrorBlock;

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

        initialize();
    }

    public void initialize(){
        setTrackingForm();
        setLogsTable();
    }

    public void setTrackingForm(){

        startDateTimeErrorBlock.setVisible(false);
        titleErrorBlock.setVisible(false);
        timerErrorBlock.setVisible(false);

        timerControlPanel.addStyleName("input-append input-prepend");
        timerControlPanel.setVisible(false);
        timerStartPanel.setVisible(true);

        setTimer( logFormData.getDuration() );
        setStartDateTime( logFormData.getStartTime() );

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
    }

    public void setTextTimer( long diff ){

        Date date = new Date(diff);
        TimeZone tz = TimeZone.createTimeZone(0);
        String formatter = DateTimeFormat.getFormat( settings.getCurrentDateTimeFormat().getTimeFormat() ).format(date, tz);
        textTimer.setText( formatter );
    }

    public void setStartDateTime( long dateTimeStamp ){

        DateTimeBoxAppended startDateTime = new DateTimeBoxAppended();

        startDateTime.addValueChangeHandler(new ValueChangeHandler<Date>() {
            @Override
            public void onValueChange( ValueChangeEvent<Date> event ) {
                LoggerView.this.logFormData.setStartTime( event.getValue().getTime() );
            }
        });

        startDateTime.setIcon( IconType.CALENDAR );
        startDateTime.setAlignment( ValueBoxBase.TextAlignment.CENTER );
        startDateTime.setAutoClose(true);
        startDateTime.setAlternateSize( AlternateSize.MEDIUM );
        startDateTime.setFormat( settings.getCurrentDateTimeFormat().getDatepickerDateTimeFormat() );
        startDateTime.setValue( new Date( dateTimeStamp ) );

        startDateTimePlaceholder.clear();
        startDateTimePlaceholder.add( startDateTime );
    }

    public void setTimer( long timeStamp ){

        DateTimeBox timer = new DateTimeBox();

        timer.addValueChangeHandler(new ValueChangeHandler<Date>() {
            @Override
            public void onValueChange( ValueChangeEvent<Date> event ) {
                Long selectedTimeStamp = event.getValue().getTime();
                Integer dayInMillis = 24 * 60 * 60 * 1000;
                //timer can get only 24h in max case
                LoggerView.this.logFormData.setDuration(
                    ( ( selectedTimeStamp + getTimeZoneOffsetInMillis() ) % dayInMillis )
                );
            }
        });

        timer.setAlignment( ValueBoxBase.TextAlignment.CENTER );
        timer.setStartView(HasViewMode.ViewMode.HOUR);
        timer.setMinView(HasViewMode.ViewMode.HOUR);
//        timer.setMinuteStep(1);
        timer.setAutoClose(true);
        timer.setAlternateSize( AlternateSize.SMALL );
        timer.setFormat( settings.getCurrentDateTimeFormat().getDatepickerTimeFormat() );
        timer.setValue( new Date( timeStamp - getTimeZoneOffsetInMillis() ) );

        Button startTimerButton = new Button();
        startTimerButton.setIcon(IconType.PLAY);
        startTimerButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                LoggerView.this.onStartTimer( event );
            }
        });

        timerStartPanel.clear();
        timerStartPanel.add( timer );
        timerStartPanel.add( startTimerButton );
    }

    public native int getTimeZoneOffsetInMillis() /*-{
        var x = new Date();
        var currentTimeZoneOffsetInMillis = x.getTimezoneOffset() * 60 * 1000;
        return -currentTimeZoneOffsetInMillis;
    }-*/;

    public void setLogsTable(){

        logsTableHandler.clear();
        logCollection.fetchAllLogs();
        List<LogModel> LOGS = logCollection.getLogCollection();

        CellTable<LogModel> table = new CellTable<LogModel>();
        table.setKeyboardSelectionPolicy(HasKeyboardSelectionPolicy.KeyboardSelectionPolicy.ENABLED);

        TextColumn<LogModel> startDateTimeColumn = new TextColumn<LogModel>() {
            @Override
            public String getValue(LogModel object) {
                Date date = new Date( object.getStartTime() );
                String formatter = DateTimeFormat
                    .getFormat(settings.getCurrentDateTimeFormat().getDateTimeFormat())
                    .format(date);
                return formatter;
            }
        };

        TextColumn<LogModel> durationColumn = new TextColumn<LogModel>() {
            @Override
            public String getValue(LogModel object) {
                Date date = new Date( object.getDuration() );
                TimeZone tz = TimeZone.createTimeZone(0);
                String formatter = DateTimeFormat
                    .getFormat( settings.getCurrentDateTimeFormat().getTimeFormat() )
                    .format(date, tz);
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
        table.addColumn(durationColumn, "Time spent");
        table.addColumn(descriptionColumn, "Address");

        table.setColumnWidth(startDateTimeColumn, 25, Style.Unit.PCT);
        table.setColumnWidth(titleColumn, 25, Style.Unit.PCT);
        table.setColumnWidth(durationColumn, 25, Style.Unit.PCT);
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

    public void clearForm(){
        setStartDateTime(new Date().getTime());
        title.setValue("");
        setTimer(0);
        description.setValue("");
    }

    public void onStartTimer( ClickEvent e ){

        timerBox.removeStyleName("control-group error");
        timerErrorBlock.setVisible(false);

        timerStartPanel.setVisible(false);
        timerControlPanel.setVisible(true);

        logFormData.setDuration( new Long(0) );
        logFormData.setStartTime( System.currentTimeMillis() );

        setTimer( logFormData.getStartTime() );
        setStartDateTime(logFormData.getStartTime());

        this.timerHandler = new Timer(){
            @Override
            public void run() {
                LoggerView.this.logFormData.setDuration( LoggerView.this.logFormData.getDuration() + 1000 );
                int timeDuration = LoggerView.this.logFormData.getDuration().intValue();
                LoggerView.this.setTextTimer(timeDuration);
            }
        };
        timerHandler.scheduleRepeating(1000);
        setTextTimer(0);
    }

    public int validateStartTime(){

        int isFailed = 0;

        if( logFormData.getStartTime() == 0){
            isFailed = 1;
            startDateBox.addStyleName("control-group error");
            startDateTimeErrorBlock.setVisible(true);
            startDateTimeErrorBlock.setText("Invalid start date");
        } else {
            startDateBox.removeStyleName("control-group error");
            startDateTimeErrorBlock.setVisible(false);
        }

        return isFailed;
    }

    public int validateTitle(){

        int isFailed = 0;
        int maxLength = 40;

        if( logFormData.getTitle().isEmpty() ){
            isFailed = 1;
            titleBox.addStyleName("control-group error");
            titleErrorBlock.setVisible(true);
            titleErrorBlock.setText("Title is empty");
        }
        if( logFormData.getTitle().length() > maxLength ){
            isFailed = 1;
            titleBox.addStyleName("control-group error");
            titleErrorBlock.setVisible(true);
            titleErrorBlock.setText("Title has to many character (" + maxLength + " max)");
        }

        if( isFailed == 0 ) {
            titleBox.removeStyleName("control-group error");
            titleErrorBlock.setVisible(false);
        }

        return isFailed;
    }

    public int validateDuration(){

        int isFailed = 0;
        int secondInMillis = 1000;

        if( logFormData.getDuration() < secondInMillis ){
            isFailed = 1;
            timerBox.addStyleName("control-group error");
            timerErrorBlock.setVisible(true);
            timerErrorBlock.setText("Timer cannot be empty");
        } else {
            timerBox.removeStyleName("control-group error");
            timerErrorBlock.setVisible(false);
        }

        return isFailed;
    }

    public boolean validateFormData(){
        Integer countFailedValidation = 0;
        countFailedValidation += validateStartTime();
        countFailedValidation += validateTitle();
        countFailedValidation += validateDuration();
        return ( countFailedValidation == 0 );
    }

    @UiHandler("title")
    public void onValueChangeTitle( ValueChangeEvent<String> e ){
        logFormData.setTitle( e.getValue() );
        validateTitle();
    }

    @UiHandler("addTimeLog")
    public void onAddTimeLog( ClickEvent e ){
        if( validateFormData() ){
            new LogModel(
                logFormData.getStartTime(),
                logFormData.getDuration(),
                title.getValue(),
                description.getValue()
            );

            resetForm();
            setLogsTable();
            logFormData.clear();
        }
    }

    @UiHandler("stopTimer")
    public void onStopTimer( ClickEvent e ){

        logFormData.saveDataToStorage();
        setTimer(logFormData.getDuration());

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
    public void onClickReset( ClickEvent e ){
        resetForm();
    }

    public void resetForm(){
        logFormData.clear();
        clearForm();
        setStartDateTime( new Date().getTime() );
    }

    public void resetView(){
        setLogsTable();
        setStartDateTime( logFormData.getStartTime());
        setTimer(logFormData.getDuration() );
    }
}
