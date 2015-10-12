package com.tracker.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Widget;
import com.tracker.client.presenter.interfaces.IMenuView;
import com.tracker.client.presenter.interfaces.IMenuView.IMenuPresenter;

public class MenuView extends ReverseCompositeView<IMenuPresenter> implements IMenuView {

    private static MenuViewUiBinder uiBinder = GWT.create( MenuViewUiBinder.class );

    interface MenuViewUiBinder extends UiBinder<Widget, MenuView> {
    }

    public MenuView() {
        initWidget( uiBinder.createAndBindUi( this ) );
    }

    @UiHandler( "logger" )
    public void onClickLogger(ClickEvent e){
        presenter.goToLogger();
    }

    @UiHandler( "analytics" )
    public void onClickAnalytics(ClickEvent e){ presenter.goToAnalytics(); }

}
