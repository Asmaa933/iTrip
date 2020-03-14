package com.andro.itrip.addTripActivity;



import android.content.Context;

import com.andro.itrip.AlarmManagerHandler;
import com.andro.itrip.FireBaseHandler;
import com.andro.itrip.GlobalApplication;
import com.andro.itrip.HelpingMethods;
import com.andro.itrip.R;
import com.andro.itrip.Trip;

import java.util.Calendar;


public class AddTripPresenter implements AddTripContract.PresenterInterface {
    private AddTripContract.ViewInterface viewInterface;

    public AddTripPresenter(AddTripContract.ViewInterface viewInterface) {
        this.viewInterface = viewInterface;
    }


    @Override
    public void addTrip(Trip trip) {
       FireBaseHandler.getInstance().addTrip(trip);
//       int requestCode = trip.getRequestId();
//       Calendar singleDate = HelpingMethods.convertToDate(trip.getStartDateTime());
//        AlarmManagerHandler.getInstance().setAlarmManager(singleDate,trip,requestCode);
//
//       if(trip.getTripType().equals(GlobalApplication.getAppContext().getString(R.string.round))){
//          Calendar roundDate =  HelpingMethods.convertToDate(trip.getRoundDateTime());
//           AlarmManagerHandler.getInstance().setAlarmManager(roundDate,trip,requestCode+1);
//
//       }

        viewInterface.sendMessage(GlobalApplication.getAppContext().getString(R.string.trip_added));
    }
    @Override
    public void onUpdate(Trip trip) {
        FireBaseHandler.getInstance().updateTrip(trip);
        viewInterface.sendMessage(GlobalApplication.getAppContext().getString(R.string.trip_edit));


    }


}
