package com.andro.itrip.ui.upcomingUI;


import com.andro.itrip.Trip;

import java.util.List;

public class UpcomingContract {
    public interface PresenterInterface {
        void getTripList();

        void updateTripList(List<Trip> trips);

        void onDelete(String tripId);

        void onCancel(Trip trip);

        void addTrip(Trip trip);

    }

    public interface ViewInterface {
        void displayTrips(List<Trip> tripList);

        void displayNoTrips();

        void displayMessage(String message);
    }
}
