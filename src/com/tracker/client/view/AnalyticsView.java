package com.tracker.client.view;

import com.github.gwtbootstrap.client.ui.Icon;
import com.github.gwtbootstrap.client.ui.Nav;
import com.github.gwtbootstrap.client.ui.NavLink;
import com.github.gwtbootstrap.client.ui.Tooltip;
import com.github.gwtbootstrap.client.ui.base.IconAnchor;
import com.github.gwtbootstrap.client.ui.constants.IconType;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.TimeZone;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.*;
import com.googlecode.gwt.charts.client.table.Table;
import com.googlecode.gwt.charts.client.table.TableOptions;
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

import java.util.ArrayList;
import java.util.Date;
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
    private Integer groupType;
    private ColumnChart chart;
    private Table table;
    private String selectedAnalyticDisplayType;
    private List<LogModel> dataCollection;
    private Long selectedPeriod;
    private String selectedGroupType;

    @Override
    public void createView() {
        initWidget(uiBinder.createAndBindUi(this));

        settings = SettingsModel.getInstance();
        selectedAnalyticDisplayType = "table";

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
                selectedAnalyticDisplayType = "table";
                redraw();
            }
        });

        IconAnchor graphIconAnchor = new IconAnchor();
        graphIconAnchor.setIcon(IconType.BAR_CHART);
        graphIconAnchor.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                selectedAnalyticDisplayType = "graph";
                redraw();
            }
        });

        groupByDay = new NavLink();
        groupByWeek = new NavLink();

        selectedPeriod = LogCollection.PERIOD_SEVEN_DAYS;
        selectedGroupType = LogCollection.GROUP_BY_DAY_PATTERN;
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
        if( selectedAnalyticDisplayType == "table" ){
            getTable();
        }
        if( selectedAnalyticDisplayType == "graph" ){
            getGraph();
        }
    }

    private void getGraph() {
        graphIconPlaceholder.setActive(true);
        tableIconPlaceholder.setActive(false);
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
        tableIconPlaceholder.setActive(true);
        graphIconPlaceholder.setActive(false);
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
                    DateTimeFormat.getFormat( settings.getCurrentDateTimeFormat().getDateFormatForAnalytics() )
                            .format( new Date(dataCollection.get(i).getStartTime()) )
            );

            Date duration = new Date(dataCollection.get(i).getDuration());
            TimeZone tz = TimeZone.createTimeZone(0);

            dataTable.setCell(
                    i, 1, dataCollection.get(i).getDuration()
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
                DateTimeFormat.getFormat( settings.getCurrentDateTimeFormat().getDateFormatForAnalytics() )
                    .format( new Date(dataCollection.get(i).getStartTime()) )
            );

            Date duration = new Date(dataCollection.get(i).getDuration());
            TimeZone tz = TimeZone.createTimeZone(0);

            dataTable.setCell(
                i, 1, dataCollection.get(i).getDuration(),
                DateTimeFormat.getFormat(settings.getCurrentDateTimeFormat().getTimeFormat())
                    .format( duration, tz )
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
        selectedGroupType = LogCollection.GROUP_BY_DAY_PATTERN;
        groupByDay.setActive(true);
        groupByWeek.setActive(false);
        redraw();
    }

    @UiHandler("groupByWeek")
    public void onClickGroupByWeek( ClickEvent e ){
        selectedGroupType = LogCollection.GROUP_BY_WEEK_PATTERN;
        groupByDay.setActive(false);
        groupByWeek.setActive(true);
        redraw();
    }

    @UiHandler("periodSevenDays")
    public void onClickPeriodSevenDays( ClickEvent e ){
        selectedPeriod = LogCollection.PERIOD_SEVEN_DAYS;
        periodSevenDays.setActive(true);
        periodMonth.setActive(false);
        periodThreeMonths.setActive(false);
        redraw();
    }

    @UiHandler("periodMonth")
    public void onClickPeriodMonth( ClickEvent e ){
        selectedPeriod = LogCollection.PERIOD_ONE_MONTH;
        periodSevenDays.setActive(false);
        periodMonth.setActive(true);
        periodThreeMonths.setActive(false);
        redraw();
    }

    @UiHandler("periodThreeMonths")
    public void onClickPeriodThreeMonths( ClickEvent e ){
        selectedPeriod = LogCollection.PERIOD_THREE_MONTHS;
        periodSevenDays.setActive(false);
        periodMonth.setActive(false);
        periodThreeMonths.setActive(true);
        redraw();
    }

    public void resetView(){
        redraw();
    }
}
