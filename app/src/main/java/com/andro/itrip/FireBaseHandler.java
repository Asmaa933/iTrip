package com.andro.itrip;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

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
    List<Trip> trips;


    private FireBaseHandler() {
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

    public List<Trip> getAllTrips(String userID) {
        trips = new ArrayList<>();
        databaseTrips = FirebaseDatabase.getInstance().getReference("trips").child(userID);
        databaseTrips.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                trips.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Trip trip = postSnapshot.getValue(Trip.class);
                    trips.add(trip);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return trips;
    }

    public void addTrip(Trip trip) {
        String tripId = databaseTrips.push().getKey();
        databaseTrips.child(tripId).setValue(trip);
    }


}
