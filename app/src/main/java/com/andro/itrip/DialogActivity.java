package com.andro.itrip;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.Settings;
import android.util.Log;

import com.andro.itrip.headUI.ChatHeadService;
import com.google.android.gms.common.internal.GmsLogger;

import java.util.ArrayList;

import okhttp3.internal.Util;

public class DialogActivity extends AppCompatActivity {
    Trip trip;
    boolean isRound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent incomingIntent = getIntent();
        if (incomingIntent != null) {
            trip = (Trip) incomingIntent.getSerializableExtra(GlobalApplication.getAppContext().getString(R.string.alarm_trip));
            isRound = incomingIntent.getBooleanExtra(getString(R.string.isRound), false);

        }
        AlertDialog.Builder Builder = new AlertDialog.Builder(this)
                .setMessage(R.string.reminder)
                .setCancelable(false)
                .setPositiveButton(R.string.start, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        double sourceLongitude = Double.parseDouble(trip.getStartLang());

                        double sourceLatitude = Double.parseDouble(trip.getStartLat());

                        double destinationLongitude = Double.parseDouble(trip.getDestinationLang());

                        double destinationLatitude = Double.parseDouble(trip.getDestinationLat());
                        String uri;
                        if (!isRound) {
                            uri = "http://maps.google.com/maps?saddr=" + sourceLatitude + "," + sourceLongitude + "&daddr=" + destinationLatitude + "," + destinationLongitude;
                        } else {
                            uri = "http://maps.google.com/maps?saddr=" + destinationLatitude + "," + destinationLongitude + "&daddr=" + sourceLatitude + "," + sourceLongitude;

                        }
                        startChatHead();

                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                        startActivity(intent);
                        stopNotification();
                        AlertReceiver.stopMedia();
                        finish();
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AlertReceiver.stopMedia();
                        stopNotification();
                    }
                })
                .setNeutralButton(R.string.snooze, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent serviceIntent = new Intent(GlobalApplication.getAppContext(), NotificationService.class);
                        serviceIntent.putExtra(GlobalApplication.getAppContext().getString(R.string.alarm_trip), trip);
                        if (isRound) {
                            serviceIntent.putExtra(getString(R.string.isRound), true);
                        }
                        serviceIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        ContextCompat.startForegroundService(GlobalApplication.getAppContext(), serviceIntent);
                        AlertReceiver.stopMedia();
                        finish();
                    }
                });
        AlertDialog alertDialog = Builder.create();
        alertDialog.show();

    }

    private void stopNotification() {
        Intent serviceIntent = new Intent(GlobalApplication.getAppContext(), NotificationService.class);
        stopService(serviceIntent);
        finish();
    }


    private void startChatHead() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(this)) {

            //If the draw over permission is not available open the settings screen
            //to grant the permission.
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:" + GlobalApplication.getAppContext().getPackageName()));
            startActivityForResult(intent, 2084);
        } else {
            Intent intent = new Intent(GlobalApplication.getAppContext(), ChatHeadService.class);
            intent.putStringArrayListExtra("notes", trip.getNotesList());
            startService(intent);
        }
    }

}



