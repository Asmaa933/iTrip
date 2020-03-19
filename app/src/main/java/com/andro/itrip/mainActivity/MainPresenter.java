package com.andro.itrip.mainActivity;

import android.view.View;
import android.widget.Toast;

import com.andro.itrip.AlarmManagerHandler;
import com.andro.itrip.FireBaseHandler;
import com.andro.itrip.GlobalApplication;
import com.andro.itrip.HelpingMethods;
import com.andro.itrip.R;
import com.andro.itrip.SavedPreferences;
import com.andro.itrip.Trip;
import com.andro.itrip.loginActivity.LoginActivity;
import com.andro.itrip.ui.upcomingUI.UpcomingPresenter;

import java.util.List;

public class MainPresenter implements MainContract.PresenterInterface {
    private MainContract.ViewInterface viewInterface;

    public MainPresenter(MainContract.ViewInterface viewInterface) {
        this.viewInterface = viewInterface;
    }

    @Override
    public void logout() {
        if (HelpingMethods.isNetworkConnected()) {

            List<Trip> tripList = UpcomingPresenter.getTrips();
            AlarmManagerHandler.getInstance().cancelAllTripsAlarm(tripList);
            FireBaseHandler.getInstance().logout();
            SavedPreferences.getInstance().resetSavedPreference();
            viewInterface.logoutSuccessful();

        } else {
            Toast.makeText(GlobalApplication.getAppContext(), GlobalApplication.getAppContext().getString(R.string.check_internet), Toast.LENGTH_LONG).show();
        }


    }


}
