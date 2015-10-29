package com.tracker.client.view;

import com.github.gwtbootstrap.client.ui.NavLink;
import com.github.gwtbootstrap.client.ui.Navbar;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Widget;
import com.tracker.client.presenter.MenuPresenter.IMenuView;
import com.tracker.client.presenter.IMenuPresenter;

public class MenuView extends ReverseCompositeView<IMenuPresenter> implements IMenuView {

    private static MenuViewUiBinder uiBinder = GWT.create( MenuViewUiBinder.class );

    interface MenuViewUiBinder extends UiBinder<Widget, MenuView> {
    }

    public MenuView() {
        initWidget( uiBinder.createAndBindUi( this ) );
    }

    @UiField
    NavLink logger;

    @UiField
    NavLink analytics;

    @UiField
    NavLink settings;

    @UiHandler( "logger" )
    public void onClickLogger(ClickEvent e){
        presenter.goToLogger();
        logger.setActive(true);
        analytics.setActive(false);
        settings.setActive(false);
    }

    @UiHandler( "analytics" )
    public void onClickAnalytics(ClickEvent e){
        presenter.goToAnalytics();
        logger.setActive(false);
        analytics.setActive(true);
        settings.setActive(false); }

    @UiHandler( "settings" )
    public void onClickSettings(ClickEvent e){
        presenter.goToSettings();
        logger.setActive(false);
        analytics.setActive(false);
        settings.setActive(true);
    }

}
