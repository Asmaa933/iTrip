package com.andro.itrip.addTripActivity;

import android.content.Context;

import com.andro.itrip.Trip;

public class AddTripContract {
    public interface PresenterInterface {
        void addTrip(Trip trip);

        void onUpdate(Trip trip);
    }

    public interface ViewInterface {
        void sendMessage(String message);
    }
}
