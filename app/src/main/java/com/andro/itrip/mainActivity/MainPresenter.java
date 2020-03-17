package com.andro.itrip.mainActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.andro.itrip.AlarmManagerHandler;
import com.andro.itrip.AlertReceiver;
import com.andro.itrip.FireBaseHandler;
import com.andro.itrip.GlobalApplication;
import com.andro.itrip.HelpingMethods;
import com.andro.itrip.Trip;
import com.andro.itrip.ui.upcomingUI.UpcomingPresenter;

import java.util.Calendar;
import java.util.List;

public class MainPresenter implements MainContract.PresenterInterface {
    private MainContract.ViewInterface viewInterface;

    public MainPresenter(MainContract.ViewInterface viewInterface) {
        this.viewInterface = viewInterface;
    }

    @Override
    public void logout() {

        List<Trip> tripList = UpcomingPresenter.gettrips();
        AlarmManagerHandler.getInstance().cancelAllTripsAlarm(tripList);
        FireBaseHandler.getInstance().logout();


        viewInterface.logoutSuccessful();
    }


}
