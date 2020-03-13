package com.andro.itrip.addTripActivity;

import com.andro.itrip.Trip;

public class AddTripContract {
    public interface PresenterInterface {
        String addTrip(Trip trip);

        void onUpdate(Trip trip);
    }

    public interface ViewInterface {

    }
}
