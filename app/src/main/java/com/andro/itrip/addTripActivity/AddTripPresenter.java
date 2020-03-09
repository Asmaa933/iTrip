package com.andro.itrip.addTripActivity;

import com.andro.itrip.FireBaseHandler;
import com.andro.itrip.Trip;

public class AddTripPresenter implements AddTripContract.PresenterInterface {
    private AddTripContract.ViewInterface viewInterface;

    public AddTripPresenter(AddTripContract.ViewInterface viewInterface) {
        this.viewInterface = viewInterface;
    }


    @Override
    public String addTrip(Trip trip) {
        String tripId = FireBaseHandler.getInstance().addTrip(trip);
        return tripId;
    }
}
