package com.andro.itrip;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.andro.itrip.ui.upcomingUI.UpcomingContract;
import com.andro.itrip.ui.upcomingUI.UpcomingPresenter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class FireBaseHandler {
    private static FireBaseHandler instance;
    DatabaseReference databaseTrips;
    List<Trip> trips ;

    private FireBaseHandler() {
        //userID mn shared
        databaseTrips = FirebaseDatabase.getInstance().getReference("trips").child("Cx4XpQrM06S9doN2mLH0cUustHj2");
    }

    public static FireBaseHandler getInstance() {

        if (instance == null) {
            synchronized (FireBaseHandler.class) {
                if (instance == null) {
                    instance = new FireBaseHandler();
                }
            }
        }
        return instance;
    }

    public void getAllTrips(final UpcomingContract.PresenterInterface presenterInterface) {
        trips = new ArrayList<>();
        databaseTrips.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                trips.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Trip trip = postSnapshot.getValue(Trip.class);
                    trips.add(trip);
                    presenterInterface.updateTripList(trips);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        presenterInterface.updateTripList(trips);


    }

    public void addTrip(Trip trip) {
        String tripId = databaseTrips.push().getKey();
        trip.setTripID(tripId);
        databaseTrips.child(tripId).setValue(trip);
    }
    public void updateTrip(Trip trip) {
      databaseTrips.child(trip.getTripID()).setValue(trip);

    }

    public  void deleteTrip(String tripId,final UpcomingContract.PresenterInterface presenterInterface){
        databaseTrips.child(tripId).removeValue();


    }

}

