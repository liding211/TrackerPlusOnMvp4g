package com.tracker.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiFactory;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Widget;
import com.tracker.client.model.LogFormDataModel;
import com.tracker.client.presenter.ILoggerPresenter;
import com.tracker.client.presenter.LoggerPresenter.ILoggerView;

public class LoggerView extends ReverseCompositeView<ILoggerPresenter> implements ILoggerView {

    private static LoggerViewUiBinder uiBinder = GWT.create( LoggerViewUiBinder.class );

    interface LoggerViewUiBinder extends UiBinder<Widget, LoggerView> {}

    @UiField
    Button addTimeLog;

    private LogFormDataModel logFormData = LogFormDataModel.getInstance();

    @Override
    public void createView() {
        //don't create the view before to take advantage of the lazy loading mechanism
        initWidget(uiBinder.createAndBindUi(this));

        addTimeLog.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                LoggerView.this.presenter.onAddLog();
            }
        });
    }

    @UiHandler( "reset" )
    public void onClickAnalytics(ClickEvent e){ presenter.onResetFormData(); }
}
