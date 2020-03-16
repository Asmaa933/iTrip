package com.andro.itrip.ui.upcomingUI;


import com.andro.itrip.AlarmManagerHandler;
import com.andro.itrip.FireBaseHandler;
import com.andro.itrip.GlobalApplication;
import com.andro.itrip.HelpingMethods;
import com.andro.itrip.R;
import com.andro.itrip.Trip;


import java.util.Calendar;
import java.util.List;

public class UpcomingPresenter implements UpcomingContract.PresenterInterface {
    private UpcomingContract.ViewInterface viewInterface;
    private List<Trip> trips;


    public UpcomingPresenter(UpcomingContract.ViewInterface viewInterface) {
        this.viewInterface = viewInterface;
    }

    @Override
    public void addTrip(Trip trip) {
        FireBaseHandler.getInstance().addTrip(trip);

    }


    @Override
    public void getTripList() {
        FireBaseHandler.getInstance().getAllTrips(this);
    }

    @Override
    public void updateTripList(List<Trip> tripList) {
        if (tripList.isEmpty()) {
            viewInterface.displayNoTrips();
        } else {
            viewInterface.displayTrips(tripList);
        }
    }


    @Override
    public void onDelete(String tripId) {
        FireBaseHandler.getInstance().deleteTrip(tripId);
    }

    @Override
    public void onUpdate(Trip trip) {
        FireBaseHandler.getInstance().updateTrip(trip);
    }


}


