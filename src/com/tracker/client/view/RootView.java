package com.tracker.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.tracker.client.presenter.interfaces.IRootView;
import com.tracker.client.presenter.interfaces.IRootView.IRootPresenter;

public class RootView extends ReverseCompositeView<IRootPresenter> implements IRootView {

    private static RootViewUiBinder uiBinder = GWT.create( RootViewUiBinder.class );

    @UiField( provided = true )
    Widget menu;

    @UiField
    SimplePanel body;

    interface RootViewUiBinder extends UiBinder<Widget, RootView> {
    }

    public RootView( MenuView menu ) {
        initWidget( uiBinder.createAndBindUi( this ) );
    }

    @Override
    public void setBody( IsWidget body ) {
        this.body.setWidget( body );
    }

}
