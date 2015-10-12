package com.tracker.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.tracker.client.presenter.interfaces.ISettingsView;
import com.tracker.client.presenter.interfaces.ISettingsView.ISettingsPresenter;

public class SettingsView extends ReverseCompositeView<ISettingsView> implements ISettingsView {

    private static SettingsViewUiBinder uiBinder = GWT.create( SettingsViewUiBinder.class );

    interface SettingsViewUiBinder extends UiBinder<Widget, SettingsView> {
    }

    @Override
    public void createView() {
        //don't create the view before to take advantage of the lazy loading mechanism
        initWidget( uiBinder.createAndBindUi( this ) );
    }

}