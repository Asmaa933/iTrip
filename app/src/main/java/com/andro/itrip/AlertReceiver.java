package com.andro.itrip;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;



public class AlertReceiver extends BroadcastReceiver {
    private static MediaPlayer mediaPlayer;

    @Override
    public void onReceive(Context context, Intent intent) {
        mediaPlayer = MediaPlayer.create(context, R.raw.alarm);
        mediaPlayer.start();

        Intent alarmIntent = new Intent(context.getApplicationContext(),DialogActivity.class);
        alarmIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.getApplicationContext().startActivity(alarmIntent);

    }
    public static void stopMedia(){
        mediaPlayer.stop();
    }

}
