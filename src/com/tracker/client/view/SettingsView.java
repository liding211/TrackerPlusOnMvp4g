package com.tracker.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Widget;
import com.tracker.client.presenter.ISettingsPresenter;
import com.tracker.client.presenter.SettingsPresenter.ISettingsView;

import com.github.gwtbootstrap.client.ui.CheckBox;
import com.github.gwtbootstrap.client.ui.ListBox;
import com.github.gwtbootstrap.client.ui.TextBox;
import com.github.gwtbootstrap.client.ui.event.HideEvent;
import com.github.gwtbootstrap.client.ui.event.ShowEvent;
import com.github.gwtbootstrap.datetimepicker.client.ui.DateTimeBox;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;

import java.util.Arrays;
import java.util.Date;

public class SettingsView extends ReverseCompositeView<ISettingsPresenter> implements ISettingsView {

//    private static SettingsViewUiBinder uiBinder = GWT.create( SettingsViewUiBinder.class );
//
//    interface SettingsViewUiBinder extends UiBinder<Widget, SettingsView> {
//    }
//
    @Override
    public void createView() {
        //don't create the view before to take advantage of the lazy loading mechanism
        initWidget( uiBinder.createAndBindUi( this ) );
    }


    @UiField
    DateTimeBox dateTimePicker;

    @UiField
    CheckBox autoClose;

    @UiField
    TextBox format;

    @UiField
    FlowPanel logViewer;

    @UiField
    ListBox weekStart;

    @UiField
    DateTimeBox valueDate;

    @UiField
    CheckBox showTodayButton;

    @UiField
    CheckBox highlightToday;


    interface DatetimepickerUiBinder extends UiBinder<Widget,SettingsView>{}

    private static DatetimepickerUiBinder uiBinder = GWT.create(DatetimepickerUiBinder.class);

    @UiHandler("showButton")
    public void onClickShowButton(ClickEvent e) {
        dateTimePicker.show();
    }

    @UiHandler("hideButton")
    public void onClickHideButton(ClickEvent e) {
        dateTimePicker.hide();
    }

    @UiHandler("autoClose")
    public void onClickAutoClose(ValueChangeEvent<Boolean> e) {
        dateTimePicker.setAutoClose(e.getValue());
        dateTimePicker.reconfigure();
    }

    @UiHandler("enabled")
    public void onClickEnabled(ValueChangeEvent<Boolean> e) {
        dateTimePicker.setEnabled(e.getValue());
    }

    @UiHandler("showTodayButton")
    public void onClickShowTodayButton(ValueChangeEvent<Boolean> e) {
        dateTimePicker.setShowTodayButton(e.getValue());
        dateTimePicker.reconfigure();
    }

    @UiHandler("highlightToday")
    public void onClickhighlightToday(ValueChangeEvent<Boolean> e) {
        dateTimePicker.setHighlightToday(e.getValue());
        dateTimePicker.reconfigure();
    }

    @UiHandler("format")
    public void onChangeFormat(ValueChangeEvent<String> e) {
        dateTimePicker.setFormat(e.getValue());
        dateTimePicker.reconfigure();
    }

    @UiHandler("language")
    public void onCHangeLanguage(ValueChangeEvent<String> e) {
        dateTimePicker.setLanguage(e.getValue());
        dateTimePicker.reconfigure();
    }

    @UiHandler("weekStart")
    public void onChangeWeekStart(ChangeEvent e) {
        dateTimePicker.setWeekStart(Integer.parseInt(weekStart.getValue()));
        dateTimePicker.reconfigure();
    }

    @UiHandler("startDate")
    public void onChangeStartDate(ValueChangeEvent<Date> e) {
        if(e.getValue() == null) {
            return;
        }

        dateTimePicker.setStartDate_(e.getValue());
        dateTimePicker.reconfigure();
    }

    @UiHandler("endDate")
    public void onChangeEndDate(ValueChangeEvent<Date> e) {
        if(e.getValue() == null) {
            return;
        }

        dateTimePicker.setEndDate_(e.getValue());
        dateTimePicker.reconfigure();
    }

    @UiHandler("valueDate")
    public void onChangeValueDate(ValueChangeEvent<Date> e) {
        dateTimePicker.setValue(e.getValue());
    }

    @UiHandler("dateTimePicker")
    public void onShow(ShowEvent e) {
        addLog("fire show event");
    }

    @UiHandler("dateTimePicker")
    public void onHide(HideEvent e) {
        addLog("fire hide event");
    }

    @UiHandler("dateTimePicker")
    public void onValueChange(ValueChangeEvent<Date> e) {
        addLog("fire value change event   [value:" + e.getValue() + "]");
    }

    public void addLog(String log) {
        logViewer.insert(new Label(new Date() + " : " + log), 0);
    }
}