package com.tracker.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Label;
import com.github.gwtbootstrap.client.ui.ListBox;
import com.tracker.client.model.LocaleDateTimeFormat;
import com.tracker.client.model.SettingsModel;
import com.tracker.client.presenter.ISettingsPresenter;
import com.tracker.client.presenter.SettingsPresenter.ISettingsView;

import java.util.HashMap;
import java.util.Map;

public class SettingsView extends ReverseCompositeView<ISettingsPresenter> implements ISettingsView {

    private static SettingsViewUiBinder uiBinder = GWT.create( SettingsViewUiBinder.class );

    interface SettingsViewUiBinder extends UiBinder<Widget, SettingsView> {
    }

    @UiField
    ListBox locales;

    @UiField
    Label dateTimeExample;

    private SettingsModel settings;

    @Override
    public void createView() {
        locales = new ListBox();
        dateTimeExample = new Label();
        settings = SettingsModel.getInstance();

        initWidget( uiBinder.createAndBindUi( this ) );
        setLocaleItems( settings.getCurrentDateTimeFormat().getName() );
    }

    public void setLocaleItems( String localeName ){
        HashMap<String, LocaleDateTimeFormat> availableDateTimeFormat = settings.getAvailableDateTimeFormat();
        for( Map.Entry<String, LocaleDateTimeFormat> localeSettings : availableDateTimeFormat.entrySet() ){
            locales.addItem( localeSettings.getKey() );
            if( localeName == localeSettings.getKey() ){
                locales.setSelectedValue( localeSettings.getKey() );
            }
        }
        setDateTimeExample();
    }

    public void setDateTimeExample(){
        dateTimeExample.setText( "Example: " + settings.getCurrentDateTimeFormat().getExample() );
    }

    @UiHandler("locales")
    public void onChangeLocales( ChangeEvent e ){
        settings.setCurrentDateTimeFormat( locales.getValue() );
        setDateTimeExample();
    }
}