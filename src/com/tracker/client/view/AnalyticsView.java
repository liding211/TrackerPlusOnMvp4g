package com.tracker.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Widget;
import com.tracker.client.presenter.AnalyticsPresenter.IAnalyticsView;

public class AnalyticsView extends ReverseCompositeView<IAnalyticsView> implements IAnalyticsView {

    private static AnalyticsViewUiBinder uiBinder = GWT.create( AnalyticsViewUiBinder.class );

    interface AnalyticsViewUiBinder extends UiBinder<Widget, AnalyticsView> {
    }

    @Override
    public void createView() {
        //don't create the view before to take advantage of the lazy loading mechanism
        initWidget( uiBinder.createAndBindUi( this ) );
    }

    public void onGoToAnalytics() {
        initWidget(uiBinder.createAndBindUi(this));
    }

}
