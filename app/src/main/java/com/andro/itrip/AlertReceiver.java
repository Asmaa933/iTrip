package com.andro.itrip;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;


public class AlertReceiver extends BroadcastReceiver {
    private static MediaPlayer mediaPlayer;
    Trip trip;

    @Override
    public void onReceive(Context context, Intent intent) {
        mediaPlayer = MediaPlayer.create(context, R.raw.alarm);
        mediaPlayer.start();

        if (intent.getAction().equals("alarm")) {
            trip = intent.getParcelableExtra(GlobalApplication.getAppContext().getString(R.string.alarm_trip));
        }

        Intent alarmIntent = new Intent(context.getApplicationContext(), DialogActivity.class);
        alarmIntent.putExtra(GlobalApplication.getAppContext().getString(R.string.alarm_trip), trip);
        alarmIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.getApplicationContext().startActivity(alarmIntent);

    }

    public static void stopMedia() {
        mediaPlayer.stop();
    }

}
