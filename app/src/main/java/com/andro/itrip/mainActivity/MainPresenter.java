package com.andro.itrip.mainActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.andro.itrip.AlertReceiver;
import com.andro.itrip.FireBaseHandler;
import com.andro.itrip.GlobalApplication;

public class MainPresenter implements MainContract.PresenterInterface {
    private MainContract.ViewInterface viewInterface;

    public MainPresenter(MainContract.ViewInterface viewInterface) {
        this.viewInterface = viewInterface;
    }

    @Override
    public void logout() {
        FireBaseHandler.getInstance().logout();
        AlarmManager alarmManager = (AlarmManager) GlobalApplication.getAppContext().getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(GlobalApplication.getAppContext(),AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(GlobalApplication.getAppContext(), 1, intent, 0);
        alarmManager.cancel(pendingIntent);
        viewInterface.logoutSuccessful();
    }


}
