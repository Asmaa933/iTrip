package com.andro.itrip.ui.upcomingUI;

import android.graphics.Movie;

import com.andro.itrip.Trip;

import java.util.List;

public class UpcomingContract {
    interface PresenterInterface {
        void getTriplist(String userId);

        void onDelete(Trip selectedTrip);

        void onUpdate(Trip updatedTrip);

        void sendMessage();

    }

    interface ViewInterface {
        void displayTrips(List<Trip> tripList);

        void displayNoTrips();

        void displayMessage(String message);
    }
}
