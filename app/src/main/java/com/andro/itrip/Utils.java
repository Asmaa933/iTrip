package com.andro.itrip;

import android.content.Context;
import android.os.Build;
import android.provider.Settings;

public class Utils {
    public static final int STATUS_CANCELLED = 0;
    public static final int STATUS_DONE = 1;
    public static final int STATUS_UPCOMING = 2;
    public static String LogTag = "henrytest";
    public static String EXTRA_MSG = "extra_msg";


    public static boolean canDrawOverlays(Context context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        } else {
            return Settings.canDrawOverlays(context);
        }


    }
}
