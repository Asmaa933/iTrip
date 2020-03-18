package com.andro.itrip.mainActivity;

import com.andro.itrip.AlarmManagerHandler;
import com.andro.itrip.FireBaseHandler;
import com.andro.itrip.SavedPreferences;
import com.andro.itrip.Trip;
import com.andro.itrip.ui.upcomingUI.UpcomingPresenter;

import java.util.List;

public class MainPresenter implements MainContract.PresenterInterface {
    private MainContract.ViewInterface viewInterface;

    public MainPresenter(MainContract.ViewInterface viewInterface) {
        this.viewInterface = viewInterface;
    }

    @Override
    public void logout() {

        List<Trip> tripList = UpcomingPresenter.getTrips();
        AlarmManagerHandler.getInstance().cancelAllTripsAlarm(tripList);
        FireBaseHandler.getInstance().logout();
        SavedPreferences.getInstance().resetSavedPreference();
        viewInterface.logoutSuccessful();
    }


}
