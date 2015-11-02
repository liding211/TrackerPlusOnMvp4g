package com.tracker.client.view;

import com.github.gwtbootstrap.client.ui.Icon;
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
import com.googlecode.gwt.charts.client.util.ChartHelper;
import com.tracker.client.model.GroupableLogCollection;
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

    private LogCollection logCollection;
    private SettingsModel settings;

    @Override
    public void createView() {
        initWidget(uiBinder.createAndBindUi(this));

        initialize();

        settings = SettingsModel.getInstance();

        logCollection = LogCollection.getInstance();
        logCollection.fetchAllLogs();
        getTable();
    }

    public void initialize(){
        IconAnchor tableIconAnchor = new IconAnchor();
        tableIconAnchor.setIcon(IconType.LIST_ALT);
        tableIconAnchor.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                getTable();
            }
        });

        IconAnchor graphIconAnchor = new IconAnchor();
        graphIconAnchor.setIcon(IconType.BAR_CHART);
        graphIconAnchor.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                getGraph();
            }
        });

        tableIconPlaceholder.add(tableIconAnchor);
        graphIconPlaceholder.add(graphIconAnchor);
    }

    private ColumnChart chart;
    private Table table;

    private void getGraph() {
        graphIconPlaceholder.setActive(true);
        tableIconPlaceholder.setActive(false);
        DataPlaceHolder.clear();
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
        DataPlaceHolder.clear();
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
        GroupableLogCollection logGroupedCollection = new GroupableLogCollection( logCollection.getLogCollection() );
        logGroupedCollection.setGroupingByPeriod( GroupableLogCollection.GROUP_BY_DAY );
        List<LogModel> dataCollection = logGroupedCollection.getGroupedCollection();

        int[] years = new int[] { 2003, 2004, 2005, 2006, 2007, 2008 };
        int[] values = new int[]{ 1336060, 1538156, 1576579, 1600652, 1968113, 1901067 };

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
        GroupableLogCollection logGroupedCollection = new GroupableLogCollection( logCollection.getLogCollection() );
        logGroupedCollection.setGroupingByPeriod( GroupableLogCollection.GROUP_BY_DAY );
        List<LogModel> dataCollection = logGroupedCollection.getGroupedCollection();

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
}
