package com.andro.itrip.addTripActivity;



import android.content.Context;

import com.andro.itrip.AlarmManagerHandler;
import com.andro.itrip.FireBaseHandler;
import com.andro.itrip.GlobalApplication;
import com.andro.itrip.R;
import com.andro.itrip.Trip;



public class AddTripPresenter implements AddTripContract.PresenterInterface {
    private AddTripContract.ViewInterface viewInterface;

    public AddTripPresenter(AddTripContract.ViewInterface viewInterface) {
        this.viewInterface = viewInterface;
    }


    @Override
    public void addTrip(Trip trip) {
       FireBaseHandler.getInstance().addTrip(trip);
//

        viewInterface.sendMessage(GlobalApplication.getAppContext().getString(R.string.trip_added));
    }
    @Override
    public void onUpdate(Trip trip) {
        FireBaseHandler.getInstance().updateTrip(trip);
        viewInterface.sendMessage(GlobalApplication.getAppContext().getString(R.string.trip_edit));


    }


}
