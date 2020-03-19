package com.andro.itrip;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;

import com.andro.itrip.mainActivity.MainActivity;
import com.andro.itrip.ui.upcomingUI.UpcomingFragment;
import com.andro.itrip.ui.upcomingUI.UpcomingPresenter;


public class AlertReceiver extends BroadcastReceiver {
    private static MediaPlayer mediaPlayer;
    Trip trip;
    boolean isRound;

    @Override
    public void onReceive(Context context, Intent intent) {
        if ("android.intent.action.BOOT_COMPLETED".equals(intent.getAction())) {
            UpcomingFragment.updateTripLists();
        }
        mediaPlayer = MediaPlayer.create(context, R.raw.alarm);
        mediaPlayer.start();

        if (intent.getAction().equals(GlobalApplication.getAppContext().getString(R.string.alarm))) {

            Bundle args = intent.getBundleExtra(GlobalApplication.getAppContext().getString(R.string.data));
            trip = (Trip) args.getSerializable(GlobalApplication.getAppContext().getString(R.string.alarm_trip));
            isRound = intent.getBooleanExtra(GlobalApplication.getAppContext().getString(R.string.isRound),false);
        }

        Intent alarmIntent = new Intent(GlobalApplication.getAppContext(),AlertDialogService.class);
        alarmIntent.putExtra(GlobalApplication.getAppContext().getString(R.string.alarm_trip), trip);
        if(isRound){
            alarmIntent.putExtra(GlobalApplication.getAppContext().getString(R.string.isRound) , true);
        }
        alarmIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        GlobalApplication.getAppContext().startService(alarmIntent);

    }

    public static void stopMedia() {
        mediaPlayer.stop();
    }

}
