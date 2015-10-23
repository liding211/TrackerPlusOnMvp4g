package com.tracker.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.core.client.GWT;
import com.mvp4g.client.Mvp4gModule;

public class TrackerEntryPoint implements EntryPoint {

//    public void onModuleLoad() {
//        Button b = new Button("Click and wait 5 seconds");
//        b.addClickHandler(this);
//
//        RootPanel.get().add(b);
//    }
//
//    public void onClick(ClickEvent event) {
//        // Create a new timer that calls Window.alert().
//        Timer t = new Timer() {
//            @Override
//            public void run() {
//                Window.alert("Nifty, eh?");
//            }
//        };
//
//        // Schedule the timer to run once in 5 seconds.
//        //t.schedule(5000);
//    }

//    /**
//     * A composite of a TextBox and a CheckBox that optionally enables it.
//     */
//    private static class OptionalTextBox extends Composite implements
//            ClickHandler {
//
//        private TextBox textBox = new TextBox();
//        private CheckBox checkBox = new CheckBox();
//
//        /**
//         * Constructs an OptionalTextBox with the given caption on the check.
//         *
//         * @param caption the caption to be displayed with the check box
//         */
//        public OptionalTextBox(String caption) {
//            // Place the check above the text box using a vertical panel.
//            VerticalPanel panel = new VerticalPanel();
//            panel.add(checkBox);
//            panel.add(textBox);
//
//            // Set the check box's caption, and check it by default.
//            checkBox.setText(caption);
//            checkBox.setChecked(true);
//            checkBox.addClickHandler(this);
//
//            // All composites must call initWidget() in their constructors.
//            initWidget(panel);
//
//            // Give the overall composite a style name.
//            //setStyleName("example-OptionalCheckBox");
//        }
//
//        public void onClick(ClickEvent event) {
//            if (event.getSource() == checkBox) {
//                // When the check box is clicked, update the text box's enabled state.
//                textBox.setEnabled(checkBox.isChecked());
//            }
//        }
//
//        /**
//         * Sets the caption associated with the check box.
//         *
//         * @param caption the check box's caption
//         */
//        public void setCaption(String caption) {
//            // Note how we use the use composition of the contained widgets to provide
//            // only the methods that we want to.
//            checkBox.setText(caption);
//        }
//
//        /**
//         * Gets the caption associated with the check box.
//         *
//         * @return the check box's caption
//         */
//        public String getCaption() {
//            return checkBox.getText();
//        }
//    }
//
//    public void onModuleLoad() {
//        // Create an optional text box and add it to the root panel.
//        OptionalTextBox otb = new OptionalTextBox("Check this to enable me");
//        RootPanel.get().add(otb);
//    }

    public void onModuleLoad() {
        Mvp4gModule module = GWT.create( Mvp4gModule.class );
        module.createAndStartModule();
        RootPanel.get().add( (Widget)module.getStartView() );
    }
}
