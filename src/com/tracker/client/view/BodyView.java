package com.tracker.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Widget;
import com.tracker.client.presenter.BodyPresenter.IBodyView;
import com.tracker.client.presenter.IBodyPresenter;

public class BodyView extends ReverseCompositeView<IBodyPresenter> implements IBodyView {

    private static BodyViewUiBinder uiBinder = GWT.create( BodyViewUiBinder.class );

    interface BodyViewUiBinder extends UiBinder<Widget, BodyView> {}

    public BodyView() {
        initWidget( uiBinder.createAndBindUi( this ) );
    }

}
