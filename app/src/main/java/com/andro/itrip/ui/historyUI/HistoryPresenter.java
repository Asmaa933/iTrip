package com.andro.itrip.ui.historyUI;

import com.andro.itrip.FireBaseHandler;
import com.andro.itrip.Trip;
import com.andro.itrip.ui.upcomingUI.UpcomingPresenter;

import java.util.List;

public class HistoryPresenter implements HistoryContract.PresenterInterface {
    private HistoryContract.ViewInterface viewInterface;

    public HistoryPresenter(HistoryContract.ViewInterface viewInterface) {
        this.viewInterface=viewInterface;
    }

    public void sendMessage() {
        String msg = "This is History fragment!!";
        viewInterface.displayMessage(msg);
    }

    @Override
    public void getTripList() {
        FireBaseHandler.getInstance().getAllPastTrips(this);
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

}
