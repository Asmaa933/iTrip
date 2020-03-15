package com.andro.itrip;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import java.util.Calendar;


public class AlarmManagerHandler {

    private static AlarmManagerHandler instance;

    private AlarmManagerHandler() {
    }

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

    public void setAlarmManager(Calendar date, Trip trip, int requestCode) {

        AlarmManager alarmManager = (AlarmManager) GlobalApplication.getAppContext().getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(GlobalApplication.getAppContext(), AlertReceiver.class);

        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(GlobalApplication.getAppContext().getString(R.string.alarm));
        Bundle bundle = new Bundle();
        bundle.putSerializable(GlobalApplication.getAppContext().getString(R.string.alarm_trip), trip);

        intent.putExtra(GlobalApplication.getAppContext().getString(R.string.data), bundle);
        if (requestCode % 2 == 1) {
            intent.putExtra(GlobalApplication.getAppContext().getString(R.string.isRound), true);
        }
        PendingIntent pendingIntent = PendingIntent.getBroadcast(GlobalApplication.getAppContext(), requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        if (System.currentTimeMillis() > date.getTimeInMillis()) {
            date.add(Calendar.DATE, 1);
        }
        if (alarmManager != null) {
            alarmManager.set(AlarmManager.RTC_WAKEUP, date.getTimeInMillis(), pendingIntent);
        }


    }


    public void cancelAlarm(Trip trip) {
        AlarmManager alarmManager = (AlarmManager) GlobalApplication.getAppContext().getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(GlobalApplication.getAppContext(), AlertReceiver.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(GlobalApplication.getAppContext().getString(R.string.alarm));
        Bundle bundle = new Bundle();
        bundle.putSerializable(GlobalApplication.getAppContext().getString(R.string.alarm_trip), trip);

        intent.putExtra(GlobalApplication.getAppContext().getString(R.string.data), bundle);
        if (trip.getRequestId()  % 2 == 1) {
            intent.putExtra(GlobalApplication.getAppContext().getString(R.string.isRound), true);
        }
        PendingIntent pendingIntent = PendingIntent.getBroadcast(GlobalApplication.getAppContext(), trip.getRequestId(), intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.cancel(pendingIntent);
        if (trip.getTripType().equals(GlobalApplication.getAppContext().getString(R.string.round_trip))) {
            pendingIntent = PendingIntent.getBroadcast(GlobalApplication.getAppContext(), trip.getRequestId() + 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            alarmManager.cancel(pendingIntent);
        }

    }
}


