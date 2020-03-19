package com.andro.itrip;

import android.app.Activity;
import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.multidex.MultiDexApplication;



public class GlobalApplication extends MultiDexApplication {

    public static final String CHANNEL_1_ID = "channel1";

    private static Context appContext;

    @Override
    public void onCreate() {
        super.onCreate();
        appContext = getApplicationContext();
        createNotificationChannels();

//        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
//
//            @Override
//            public void onActivityCreated(Activity activity,
//                                          Bundle savedInstanceState) {
//
//                // new activity created; force its orientation to portrait
//                activity.setRequestedOrientation(
//                        ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//
//            }
//
//            @Override
//            public void onActivityStarted(@NonNull Activity activity) {
//                activity.setRequestedOrientation(
//                        ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//            }
//
//            @Override
//            public void onActivityResumed(@NonNull Activity activity) {
//                activity.setRequestedOrientation(
//                        ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//            }
//
//            @Override
//            public void onActivityPaused(@NonNull Activity activity) {
//                activity.setRequestedOrientation(
//                        ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//            }
//
//            @Override
//            public void onActivityStopped(@NonNull Activity activity) {
//                activity.setRequestedOrientation(
//                        ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//            }
//
//            @Override
//            public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {
//                activity.setRequestedOrientation(
//                        ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//            }
//
//            @Override
//            public void onActivityDestroyed(@NonNull Activity activity) {
//                activity.setRequestedOrientation(
//                        ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//            }
//        });
    }

    public static Context getAppContext() {
        return appContext;
    }
    private void createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel1 = new NotificationChannel(
                    CHANNEL_1_ID,
                    "Channel 1",
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel1.setDescription("This is Channel 1");

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel1);
        }
    }
}

