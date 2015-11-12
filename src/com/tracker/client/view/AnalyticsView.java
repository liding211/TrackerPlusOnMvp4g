package com.tracker.client.view;

import com.github.gwtbootstrap.client.ui.NavLink;
import com.github.gwtbootstrap.client.ui.base.IconAnchor;
import com.github.gwtbootstrap.client.ui.constants.IconType;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;
import com.googlecode.gwt.charts.client.table.Table;
import com.googlecode.gwt.charts.client.table.TableOptions;
import com.tracker.client.helper.HistoryHelper;
import com.tracker.client.helper.TimeHelper;
import com.tracker.client.model.LogCollection;
import com.tracker.client.model.LogModel;
import com.tracker.client.model.SettingsModel;
import com.tracker.client.presenter.AnalyticsPresenter.IAnalyticsView;
import com.tracker.client.presenter.IAnalyticsPresenter;

import com.googlecode.gwt.charts.client.ChartLoader;
import com.googlecode.gwt.charts.client.ChartPackage;
import com.googlecode.gwt.charts.client.ColumnType;
import com.googlecode.gwt.charts.client.DataTable;
import com.googlecode.gwt.charts.client.corechart.ColumnChart;
import com.googlecode.gwt.charts.client.corechart.ColumnChartOptions;
import com.googlecode.gwt.charts.client.options.HAxis;
import com.googlecode.gwt.charts.client.options.VAxis;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class AnalyticsView extends ReverseCompositeView<IAnalyticsPresenter> implements IAnalyticsView {

    private static AnalyticsViewUiBinder uiBinder = GWT.create( AnalyticsViewUiBinder.class );

    interface AnalyticsViewUiBinder extends UiBinder<Widget, AnalyticsView> {
    }

    @UiField
    HTMLPanel DataPlaceHolder;

    @UiField
    NavLink tableIconPlaceholder;

    @UiField
    NavLink graphIconPlaceholder;

    @UiField
    NavLink groupByDay;

    @UiField
    NavLink groupByWeek;

    @UiField
    NavLink periodSevenDays;

    @UiField
    NavLink periodMonth;

    @UiField
    NavLink periodThreeMonths;

    private LogCollection logCollection;
    private SettingsModel settings;
    private ColumnChart chart;
    private Table table;
    private String selectedAnalyticDisplayType;
    private List<LogModel> dataCollection;
    private Long selectedPeriod;
    private String selectedGroupType;
    private Boolean hasHistory;
    private static String PERIOD_SEVEN_DAYS = "week";
    private static String PERIOD_ONE_MONTH = "month";
    private static String PERIOD_THREE_MONTHS = "three_month";
    private static String GROUP_BY_DAY = "day";
    private static String GROUP_BY_WEEK = "week";
    private static String DISPLAY_TYPE_TABLE = "table";
    private static String DISPLAY_TYPE_GRAPH = "graph";

    @Override
    public void createView() {
        initWidget(uiBinder.createAndBindUi(this));

        settings = SettingsModel.getInstance();
        selectedAnalyticDisplayType = AnalyticsView.DISPLAY_TYPE_TABLE;

        logCollection = LogCollection.getInstance();
        logCollection.fetchAllLogs();

        initialize();
        redraw();
    }

    public void initialize(){
        IconAnchor tableIconAnchor = new IconAnchor();
        tableIconAnchor.setIcon(IconType.LIST_ALT);
        tableIconAnchor.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                History.newItem(HistoryHelper.addParamToCurrentToken( "display_type", AnalyticsView.DISPLAY_TYPE_TABLE ), false);
                selectedAnalyticDisplayType = AnalyticsView.DISPLAY_TYPE_TABLE;
                redraw();
            }
        });

        IconAnchor graphIconAnchor = new IconAnchor();
        graphIconAnchor.setIcon(IconType.BAR_CHART);
        graphIconAnchor.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
            History.newItem(HistoryHelper.addParamToCurrentToken( "display_type", AnalyticsView.DISPLAY_TYPE_GRAPH ), false);
            selectedAnalyticDisplayType = AnalyticsView.DISPLAY_TYPE_GRAPH;
            redraw();
            }
        });

        selectedPeriod = LogCollection.PERIOD_SEVEN_DAYS;
        selectedGroupType = LogCollection.GROUP_BY_DAY_PATTERN;
        periodSevenDays.setActive(true);
        groupByDay.setActive(true);

        tableIconPlaceholder.add(tableIconAnchor);
        graphIconPlaceholder.add(graphIconAnchor);
    }

    public void redraw(){

        dataCollection = LogCollection.getGroupCollectionByPattern(
            LogCollection.getCollectionByPeriod(
                logCollection.getLogCollection(),
                selectedPeriod
            ),
            selectedGroupType
        );
        DataPlaceHolder.clear();
        if( selectedAnalyticDisplayType == AnalyticsView.DISPLAY_TYPE_TABLE ){
            getTable();
        }
        if( selectedAnalyticDisplayType == AnalyticsView.DISPLAY_TYPE_GRAPH ){
            getGraph();
        }
    }

    private void getGraph() {
        setParams( HistoryHelper.getUrlParams( History.getToken() ));
        ChartLoader chartLoader = new ChartLoader(ChartPackage.CORECHART);
        chartLoader.loadApi(new Runnable() {

            @Override
            public void run() {
                // Create and attach the chart
                chart = new ColumnChart();
                drawGraph();
                DataPlaceHolder.add(chart);
            }
        });
    }

    private void getTable() {
        setParams( HistoryHelper.getUrlParams( History.getToken() ));
        ChartLoader chartLoader = new ChartLoader(ChartPackage.TABLE);
        chartLoader.loadApi(new Runnable() {

            @Override
            public void run() {
                // Create and attach the chart
                table = new Table();
                drawTable();
                DataPlaceHolder.add(table);
            }
        });
    }

    private void drawGraph() {

        // Prepare the data
        DataTable dataTable = DataTable.create();
        dataTable.addColumn(ColumnType.STRING, "Year");
        dataTable.addColumn(ColumnType.NUMBER);


        dataTable.addRows(dataCollection.size());

        for(int i = 0; i < dataCollection.size(); i++){
            dataTable.setCell(
                i, 0,
                selectedGroupType == LogCollection.GROUP_BY_DAY_PATTERN ?
                    DateTimeFormat.getFormat( settings.getCurrentDateTimeFormat().getDateFormatForAnalytics() )
                        .format(new Date(dataCollection.get(i).getStartTime()))
                    : TimeHelper.getWeekRangeTitleByDate( dataCollection.get(i).getStartTime(), settings )

            );

            dataTable.setCell(
                    i, 1, dataCollection.get(i).getDuration(),
                    TimeHelper.getTimeByTimeStamp( dataCollection.get(i).getDuration(), settings )
            );
        }

        // Set options
        ColumnChartOptions options = ColumnChartOptions.create();
        options.setFontName("Tahoma");
        options.setTitle("Time logger");
        options.setHAxis(HAxis.create("Date"));
        options.setVAxis(VAxis.create("Time spent"));

        // Draw the chart
        chart.draw(dataTable, options);
    }

    private void drawTable() {

        // Prepare the data
        DataTable dataTable = DataTable.create();
        dataTable.addColumn(ColumnType.NUMBER, "Date");
        dataTable.addColumn(ColumnType.NUMBER, "Spent time");
        dataTable.addRows(dataCollection.size());
        for(int i = 0; i < dataCollection.size(); i++){
            dataTable.setCell(
                i, 0, dataCollection.get(i).getStartTime(),
                selectedGroupType == LogCollection.GROUP_BY_DAY_PATTERN ?
                    DateTimeFormat.getFormat( settings.getCurrentDateTimeFormat().getDateFormatForAnalytics() )
                        .format(new Date(dataCollection.get(i).getStartTime()))
                    : TimeHelper.getWeekRangeTitleByDate( dataCollection.get(i).getStartTime(), settings )
            );

            dataTable.setCell(
                i, 1, dataCollection.get(i).getDuration(),
                TimeHelper.getTimeByTimeStamp( dataCollection.get(i).getDuration(), settings )
            );
        }

        // Set options
        TableOptions options = TableOptions.create();
        options.setAlternatingRowStyle(true);

        // Draw the chart
        table.draw(dataTable, options);
    }

    @UiHandler("groupByDay")
    public void onClickGroupByDay( ClickEvent e ){
        History.newItem(HistoryHelper.addParamToCurrentToken( "group", AnalyticsView.GROUP_BY_DAY ), false);
        selectedGroupType = LogCollection.GROUP_BY_DAY_PATTERN;
        resetView();
    }

    @UiHandler("groupByWeek")
    public void onClickGroupByWeek( ClickEvent e ){
        History.newItem(HistoryHelper.addParamToCurrentToken( "group", AnalyticsView.GROUP_BY_WEEK ), false);
        selectedGroupType = LogCollection.GROUP_BY_WEEK_PATTERN;
        resetView();
    }

    @UiHandler("periodSevenDays")
    public void onClickPeriodSevenDays( ClickEvent e ){
        History.newItem(HistoryHelper.addParamToCurrentToken( "period", AnalyticsView.PERIOD_SEVEN_DAYS ), false);
        selectedPeriod = LogCollection.PERIOD_SEVEN_DAYS;
        resetView();
    }

    @UiHandler("periodMonth")
    public void onClickPeriodMonth(ClickEvent e ){
        History.newItem(HistoryHelper.addParamToCurrentToken( "period", AnalyticsView.PERIOD_ONE_MONTH ), false);
        selectedPeriod = LogCollection.PERIOD_ONE_MONTH;
        resetView();
    }

    @UiHandler("periodThreeMonths")
    public void onClickPeriodThreeMonths( ClickEvent e ){
        History.newItem(HistoryHelper.addParamToCurrentToken( "period", AnalyticsView.PERIOD_THREE_MONTHS ), false);
        selectedPeriod = LogCollection.PERIOD_THREE_MONTHS;
        resetView();
    }

    public void setParams( String paramString ){

        resetButtons();

        HashMap<String, String> params = HistoryHelper.unserializeParams( paramString );

        if( params.containsKey( "period" ) ){
            if( params.get( "period" ).equals( AnalyticsView.PERIOD_SEVEN_DAYS ) ){
                selectedPeriod = LogCollection.PERIOD_SEVEN_DAYS;
                periodSevenDays.setActive(true);
            }
            if( params.get( "period" ).equals( AnalyticsView.PERIOD_ONE_MONTH ) ){
                selectedPeriod = LogCollection.PERIOD_ONE_MONTH;
                periodMonth.setActive(true);
            }
            if( params.get( "period" ).equals( AnalyticsView.PERIOD_THREE_MONTHS ) ){
                selectedPeriod = LogCollection.PERIOD_THREE_MONTHS;
                periodThreeMonths.setActive(true);
            }
        } else {
            //by default
            selectedPeriod = LogCollection.PERIOD_SEVEN_DAYS;
            periodSevenDays.setActive(true);
        }

        if( params.containsKey( "group" ) ){
            if( params.get( "group" ).equals( AnalyticsView.GROUP_BY_DAY ) ){
                selectedGroupType = LogCollection.GROUP_BY_DAY_PATTERN;
                groupByDay.setActive(true);
            }
            if( params.get( "group" ).equals( AnalyticsView.GROUP_BY_WEEK ) ){
                selectedGroupType = LogCollection.GROUP_BY_WEEK_PATTERN;
                groupByWeek.setActive(true);
            }
        } else {
            //by default
            selectedGroupType = LogCollection.GROUP_BY_DAY_PATTERN;
            groupByDay.setActive(true);
        }

        if( params.containsKey( "display_type" ) ){
            if( params.get( "display_type" ).equals( AnalyticsView.DISPLAY_TYPE_TABLE ) ){
                selectedAnalyticDisplayType = AnalyticsView.DISPLAY_TYPE_TABLE;
                tableIconPlaceholder.setActive(true);
            }
            if( params.get( "display_type" ).equals( AnalyticsView.DISPLAY_TYPE_GRAPH ) ){
                selectedAnalyticDisplayType = AnalyticsView.DISPLAY_TYPE_GRAPH;
                graphIconPlaceholder.setActive(true);
            }
        } else {
            //by default
            selectedAnalyticDisplayType = AnalyticsView.DISPLAY_TYPE_TABLE;
            tableIconPlaceholder.setActive(true);
        }
    }

    public void resetButtons(){

        periodSevenDays.setActive(false);
        periodMonth.setActive(false);
        periodThreeMonths.setActive(false);

        groupByDay.setActive(false);
        groupByWeek.setActive(false);

        tableIconPlaceholder.setActive(false);
        graphIconPlaceholder.setActive(false);
    }

    public void resetView(){
        setParams( HistoryHelper.getUrlParams( History.getToken() ));
        redraw();
    }
}
