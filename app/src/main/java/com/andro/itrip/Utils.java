package com.andro.itrip;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import android.provider.Settings;

import androidx.core.app.ActivityCompat;

public class Utils {
    public static final int STATUS_CANCELLED = 0;
    public static final int STATUS_DONE = 1;
    public static final int STATUS_UPCOMING = 2;
    public static String LogTag = "henrytest";
    public static String EXTRA_MSG = "extra_msg";
    public static int OVERLAY_PERMISSION_REQ_CODE_CHATHEAD = 1234;
    public static int OVERLAY_PERMISSION_REQ_CODE_CHATHEAD_MSG = 5678;
    public static int PERMISSION_ID = 44;


    public static boolean canDrawOverlays(Context context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        } else {
            return Settings.canDrawOverlays(context);
        }


    }

    public static boolean checkPermissions() {
        if (ActivityCompat.checkSelfPermission(GlobalApplication.getAppContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(GlobalApplication.getAppContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(GlobalApplication.getAppContext(), Manifest.permission.INTERNET) == PackageManager.PERMISSION_GRANTED)
        {

            return true;
        }
        return false;
    }


    public static void requestPermissions(Activity activity) {
        ActivityCompat.requestPermissions(
                activity,
                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.INTERNET},
                PERMISSION_ID
        );
    }
    public static boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) GlobalApplication.getAppContext().getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
                LocationManager.NETWORK_PROVIDER
        );
    }
}
