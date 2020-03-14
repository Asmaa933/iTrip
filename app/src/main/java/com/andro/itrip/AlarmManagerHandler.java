package com.andro.itrip;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.view.View;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AlarmManagerHandler {

    private static AlarmManagerHandler instance;
    private AlarmManagerHandler(){}

    public static AlarmManagerHandler getInstance() {

        if (instance == null) {
            synchronized (AlarmManagerHandler.class) {
                if (instance == null) {
                    instance = new AlarmManagerHandler();
                }
            }
        }
        return instance;
    }

    public void setAlarmManager(Calendar date, Trip trip,int requestCode) {

        AlarmManager alarmManager = (AlarmManager) GlobalApplication.getAppContext().getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(GlobalApplication.getAppContext(), AlertReceiver.class);

        intent.putExtra(GlobalApplication.getAppContext().getString(R.string.alarm_trip), trip);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction("alarm");
        GlobalApplication.getAppContext().sendBroadcast(intent);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(GlobalApplication.getAppContext(),requestCode , intent, 0);

        if (date.before(Calendar.getInstance())) {
            date.add(Calendar.DATE, 1);

        }
        if (alarmManager != null) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, date.getTimeInMillis(), pendingIntent);
        }


    }


    public void cancelAlarm(Trip trip) {
        AlarmManager alarmManager = (AlarmManager) GlobalApplication.getAppContext().getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(GlobalApplication.getAppContext(), AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(GlobalApplication.getAppContext(), trip.getRequestId() , intent, 0);

        alarmManager.cancel(pendingIntent);
         pendingIntent = PendingIntent.getBroadcast(GlobalApplication.getAppContext(), trip.getRequestId()+1 , intent, 0);
        alarmManager.cancel(pendingIntent);

    }
}