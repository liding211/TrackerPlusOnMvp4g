package com.tracker.client.view;

import com.github.gwtbootstrap.client.ui.NavLink;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Widget;
import com.tracker.client.helper.HistoryHelper;
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
    NavLink tracker;

    @UiField
    NavLink analytics;

    @UiField
    NavLink settings;

    public void setSelectedPage(){
        tracker.setActive(false);
        analytics.setActive(false);
        settings.setActive(false);

        String page = HistoryHelper.getBaseUrl();
        if( page.equals( "tracker" ) ){
            tracker.setActive(true);
        }
        if( page.equals( "analytics" ) ){
            analytics.setActive(true);
        }
        if( page.equals( "settings" ) ){
            settings.setActive(true);
        }
    }

    @UiHandler( "tracker" )
    public void onClickLogger(ClickEvent e){
        presenter.goToLogger();
        setSelectedPage();
    }

    @UiHandler( "analytics" )
    public void onClickAnalytics(ClickEvent e){
        presenter.goToAnalytics();
        setSelectedPage();
    }

    @UiHandler( "settings" )
    public void onClickSettings(ClickEvent e){
        presenter.goToSettings();
        setSelectedPage();
    }

    public void reset(){
        setSelectedPage();
    }

}
