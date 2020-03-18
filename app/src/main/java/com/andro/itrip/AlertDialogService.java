package com.andro.itrip;

import android.app.Service;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.andro.itrip.headUI.ChatHeadService;
import com.andro.itrip.ui.upcomingUI.UpcomingFragment;

public class AlertDialogService extends Service {
    private WindowManager windowManager;
    private LinearLayout alert;
    private Point szWindow = new Point();
    private TextView titleTxt, descTxt;
    private Button startBtn, snoozeBtn, cancelBtn;


    private Trip trip;
    boolean isRound;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            trip = (Trip) intent.getSerializableExtra(GlobalApplication.getAppContext().getString(R.string.alarm_trip));
            isRound = intent.getBooleanExtra(getString(R.string.isRound), false);

        }
        handleStart();
        return super.onStartCommand(intent, flags, startId);
    }


    private void handleStart() {
        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);

        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);

        alert = (LinearLayout) inflater.inflate(R.layout.alert_dialog, null);
        startBtn = alert.findViewById(R.id.startButton);
        snoozeBtn = alert.findViewById(R.id.snoozeButton);
        cancelBtn = alert.findViewById(R.id.cancelButton);
        titleTxt = alert.findViewById(R.id.titleTxt);
        descTxt = alert.findViewById(R.id.descTxt);


        int LAYOUT_FLAG;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            LAYOUT_FLAG = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
            LAYOUT_FLAG = WindowManager.LayoutParams.TYPE_PHONE;
        }

        WindowManager.LayoutParams alertParam = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                LAYOUT_FLAG,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                PixelFormat.TRANSLUCENT);
        alertParam.gravity = Gravity.CENTER;
        windowManager.addView(alert, alertParam);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            windowManager.getDefaultDisplay().getSize(szWindow);
        } else {
            int w = windowManager.getDefaultDisplay().getWidth();
            int h = windowManager.getDefaultDisplay().getHeight();
            szWindow.set(w, h);
        }
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                trip.setStatus(1);
                FireBaseHandler.getInstance().updateTrip(trip);
                AlarmManagerHandler.getInstance().cancelAlarm(trip);

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
                if (alert != null) {
                    windowManager.removeView(alert);
                }
            }
        });
        snoozeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent serviceIntent = new Intent(GlobalApplication.getAppContext(), NotificationService.class);
                serviceIntent.putExtra(GlobalApplication.getAppContext().getString(R.string.alarm_trip), trip);
                if (isRound) {
                    serviceIntent.putExtra(getString(R.string.isRound), true);
                }
                serviceIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                ContextCompat.startForegroundService(GlobalApplication.getAppContext(), serviceIntent);
                AlertReceiver.stopMedia();
                if (alert != null) {
                    windowManager.removeView(alert);
                }

            }
        });


        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                trip.setStatus(0);
                FireBaseHandler.getInstance().updateTrip(trip);
                UpcomingFragment.updateTripLists();
                AlarmManagerHandler.getInstance().cancelAlarm(trip);

                AlertReceiver.stopMedia();
                stopNotification();

            }
        });
        titleTxt.setText(trip.getTripTitle());
        descTxt.setText(getString(R.string.reminder));
    }

    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();


    }

    private void stopNotification() {
        Intent serviceIntent = new Intent(GlobalApplication.getAppContext(), NotificationService.class);
        stopService(serviceIntent);
        if (alert != null) {
            windowManager.removeView(alert);
        }
    }


    private void startChatHead() {
        Intent intent = new Intent(GlobalApplication.getAppContext(), ChatHeadService.class);
        intent.putStringArrayListExtra("notes", trip.getNotesList());
        startService(intent);
    }

}
