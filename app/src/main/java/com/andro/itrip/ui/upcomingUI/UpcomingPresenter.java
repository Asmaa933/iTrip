package com.andro.itrip.ui.upcomingUI;


import com.andro.itrip.FireBaseHandler;
import com.andro.itrip.Trip;


import java.util.List;

public class UpcomingPresenter implements UpcomingContract.PresenterInterface {
    private UpcomingContract.ViewInterface viewInterface;
    private static List<Trip> trips;
    private static Trip selectedTripData;


    public UpcomingPresenter(UpcomingContract.ViewInterface viewInterface) {
        this.viewInterface = viewInterface;
    }

    @Override
    public String addTrip(Trip trip) {
        return FireBaseHandler.getInstance().addTrip(trip);
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
            trips = tripList;
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

    public static List<Trip> getTrips() {

        return trips;
    }

}



