package com.alifesoftware.httpautocompletetextviewapp;

import okhttp3.OkHttpClient;

/**
 * Created by Anuj Saluja on 8/6/16.
 *
 * Utility class to get instance of OkHttpClient
 * for network communication
 */

public class OkHttpFactory {
    // Single instance of OkHttpClient
    private static OkHttpClient httpClient = null;

    /**
     * Method to return instance of OkHttpClient
     * @return
     */
    public static OkHttpClient getOkHttpClient() {
        // Create new OkHttpClient if we do not have one
        if(httpClient == null) {
            httpClient = new OkHttpClient();
        }

        return httpClient;
    }
}
