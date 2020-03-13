package com.andro.itrip;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

public class DialogActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AlertDialog.Builder Builder = new AlertDialog.Builder(this)
                .setMessage("Reminder for your trip!!!")
                .setCancelable(false)
                .setPositiveButton("Start", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        //test
                        double sourceLongitude = 31.2357;

                        double sourceLatitude = 30.0444;

                        double destinationLongitude = 32.2715;

                        double destinationLatitude = 30.5965;
                        String uri = "http://maps.google.com/maps?saddr=" + sourceLatitude + "," + sourceLongitude + "&daddr=" + destinationLatitude + "," + destinationLongitude;
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                        startActivity(intent);
                        stopNotification();
                        AlertReceiver.stopMedia();
                        finish();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AlertReceiver.stopMedia();
                        stopNotification();
                    }
                })
                .setNeutralButton("Snooze", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent serviceIntent = new Intent(GlobalApplication.getAppContext(), NotificationService.class);
                        serviceIntent.putExtra("inputExtra", "INPUT FROM ACTIVITY");
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
}
