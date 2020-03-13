package com.andro.itrip;

import android.content.Context;
import android.net.ConnectivityManager;

public class HelpingMethods {
    public static boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) GlobalApplication.getAppContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }
}
