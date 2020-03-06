package com.andro.itrip.ui.upcomingUI;


import com.andro.itrip.FireBaseHandler;
import com.andro.itrip.Trip;


import java.util.List;

public class UpcomingPresenter implements UpcomingContract.PresenterInterface {
    UpcomingContract.ViewInterface viewInterface;
    List<Trip> trips;


    public UpcomingPresenter(UpcomingContract.ViewInterface viewInterface) {
        this.viewInterface = viewInterface;
    }


    @Override
    public void getTripList() {
        trips = FireBaseHandler.getInstance().getAllTrips(this);
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
    public void onDelete(Trip selectedTrip) {

    }

    @Override
    public void onUpdate(Trip updatedTrip) {

    }
}
