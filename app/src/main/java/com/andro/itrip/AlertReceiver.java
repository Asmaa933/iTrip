package com.andro.itrip;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;

import com.andro.itrip.mainActivity.MainActivity;


public class AlertReceiver extends BroadcastReceiver {
    private static MediaPlayer mediaPlayer;
    Trip trip;
    boolean isRound;

    @Override
    public void onReceive(Context context, Intent intent) {
        mediaPlayer = MediaPlayer.create(context, R.raw.alarm);
        mediaPlayer.start();

        if (intent.getAction().equals(GlobalApplication.getAppContext().getString(R.string.alarm))) {

            Bundle args = intent.getBundleExtra(GlobalApplication.getAppContext().getString(R.string.data));
            trip = (Trip) args.getSerializable(GlobalApplication.getAppContext().getString(R.string.alarm_trip));
            isRound = intent.getBooleanExtra(GlobalApplication.getAppContext().getString(R.string.isRound),false);
        }

        Intent alarmIntent = new Intent(context,DialogActivity.class);
        alarmIntent.putExtra(GlobalApplication.getAppContext().getString(R.string.alarm_trip), trip);
        if(isRound){
            alarmIntent.putExtra(GlobalApplication.getAppContext().getString(R.string.isRound) , true);
        }
        alarmIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(alarmIntent);

    }

    public static void stopMedia() {
        mediaPlayer.stop();
    }

}
