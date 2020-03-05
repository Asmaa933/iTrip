package com.andro.itrip.ui.upcomingUI;

import com.andro.itrip.FireBaseHandler;
import com.andro.itrip.Trip;

import java.util.List;

public class UpcomingPresenter implements UpcomingContract.PresenterInterface{
    UpcomingContract.ViewInterface viewInterface;
    List<Trip> trips;

    public UpcomingPresenter(UpcomingContract.ViewInterface viewInterface) {
        this.viewInterface=viewInterface;
    }

    public void sendMessage() {
        String msg = "This is Upcoming fragment!!";
        viewInterface.displayMessage(msg);
    }

    @Override
    public void getTriplist(String userId) {
    trips = FireBaseHandler.getInstance().getAllTrips(userId);
    if (trips.isEmpty()){
        viewInterface.displayNoTrips();
    }
    else {
        viewInterface.displayTrips(trips);
    }
    }

    @Override
    public void onDelete(Trip selectedTrip) {

    }

    @Override
    public void onUpdate(Trip updatedTrip) {

    }
}
