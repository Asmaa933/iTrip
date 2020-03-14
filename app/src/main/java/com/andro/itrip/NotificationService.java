package com.andro.itrip;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;


public class NotificationService extends Service {
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


           Trip trip = intent.getParcelableExtra(GlobalApplication.getAppContext().getString(R.string.alarm_trip));


        Intent notificationIntent = new Intent(this, DialogActivity.class);
        notificationIntent.putExtra(GlobalApplication.getAppContext().getString(R.string.alarm_trip), trip);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0, notificationIntent,  PendingIntent.FLAG_UPDATE_CURRENT);


        Notification notification = new NotificationCompat.Builder(this, GlobalApplication.CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_menu_camera)
                .setContentTitle(getString(R.string.alarm_trip))
                .setContentText(trip.getTripTitle())
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setContentIntent(pendingIntent)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .build();


        startForeground(1, notification);

        return START_NOT_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
