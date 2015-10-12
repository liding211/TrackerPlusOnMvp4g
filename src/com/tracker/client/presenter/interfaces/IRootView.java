package com.tracker.client.presenter.interfaces;

import com.google.gwt.user.client.ui.IsWidget;

public interface IRootView extends IsWidget {

    public interface IRootPresenter {

    }

    void setMenu( IsWidget menu );

    void setBody( IsWidget body );

}
