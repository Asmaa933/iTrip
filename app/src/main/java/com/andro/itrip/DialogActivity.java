package com.andro.itrip;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

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
                        if(isRound){
                            serviceIntent.putExtra(getString(R.string.isRound),true);
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
}
