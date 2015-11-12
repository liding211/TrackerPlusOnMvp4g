package com.tracker.client.helper;

import com.google.gwt.http.client.URL;

public class StringHelper {

    public static String urlEncode( String value ){

        String encodedValue = "";
        encodedValue = URL.encode( value );
        return encodedValue;
    }
}
