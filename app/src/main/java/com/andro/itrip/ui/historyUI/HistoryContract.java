package com.andro.itrip.ui.historyUI;

import com.andro.itrip.Trip;

import java.util.List;

public class HistoryContract {

    public interface PresenterInterface {
        void getTripList();

        void updateTripList(List<Trip> trips);

        void onDelete(String tripId);
    }

    public interface ViewInterface {
        void displayTrips(List<Trip> tripList);

        void displayNoTrips();

        void displayMessage(String message);
    }

}
