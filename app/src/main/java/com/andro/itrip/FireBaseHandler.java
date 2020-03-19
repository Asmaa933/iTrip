package com.andro.itrip;

import android.app.Activity;

import androidx.annotation.NonNull;

import com.andro.itrip.registerActivity.RegisterContract;
import com.andro.itrip.loginActivity.LoginContract;
import com.andro.itrip.ui.historyUI.HistoryPresenter;
import com.andro.itrip.ui.upcomingUI.UpcomingPresenter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class FireBaseHandler {
    private static FireBaseHandler instance;
    private DatabaseReference databaseTrips;
    private FirebaseAuth auth;
    private List<Trip> trips;
   // private List<Trip> selectedTrip;

    private FireBaseHandler() {
        auth = FirebaseAuth.getInstance();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
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


    public List<Trip> getAllTrips(final UpcomingPresenter presenterInterface) {
        trips = new ArrayList<>();
        databaseTrips = FirebaseDatabase.getInstance().getReference("trips").child(SavedPreferences.getInstance().readUserID());
        databaseTrips.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                trips.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Trip trip = postSnapshot.getValue(Trip.class);
                    if (trip.getStatus() == Utils.STATUS_UPCOMING) {
                        Calendar chosenSingleDate = HelpingMethods.convertToDate(trip.getStartDateTime());
                        AlarmManagerHandler.getInstance().setAlarmManager(chosenSingleDate, trip, trip.getRequestId());
                        if (trip.getTripType().equals(GlobalApplication.getAppContext().getString(R.string.round_trip))) {
                            Calendar chosenRoundDate = HelpingMethods.convertToDate(trip.getRoundDateTime());
                            AlarmManagerHandler.getInstance().setAlarmManager(chosenRoundDate, trip, trip.getRequestId() + 1);
                        }
                        trips.add(trip);
                        presenterInterface.updateTripList(trips);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        presenterInterface.updateTripList(trips);
        return trips;
    }

    public List<Trip> getAllPastTrips(final HistoryPresenter presenterInterface) {
        trips = new ArrayList<>();
        databaseTrips = FirebaseDatabase.getInstance().getReference("trips").child(SavedPreferences.getInstance().readUserID());
        databaseTrips.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                trips.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Trip trip = postSnapshot.getValue(Trip.class);
                    if (trip.getStatus() != Utils.STATUS_UPCOMING) {
                        trips.add(trip);
                        presenterInterface.updateTripList(trips);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        presenterInterface.updateTripList(trips);
        return trips;

    }

//    public void getTripByID(final UpcomingPresenter presenterInterface, final String tripID) {
//        selectedTrip = new ArrayList<>();
//        databaseTrips = FirebaseDatabase.getInstance().getReference("trips").child(SavedPreferences.getInstance().readUserID());
//        databaseTrips.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                selectedTrip.clear();
//                for (DataSnapshot item : dataSnapshot.getChildren()) {
//                    if (item.getValue().equals(tripID)) {
//                        Trip trip = dataSnapshot.getValue(Trip.class);
//                        selectedTrip.add(trip);
//                        presenterInterface.updateTripDate(selectedTrip.get(0));
//                    }
//                }
//
//            }


//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//            }
//        });
//
//
//    }

    public String addTrip(Trip trip) {
        String tripId = databaseTrips.push().getKey();
        if (tripId != null) {
            trip.setTripID(tripId);
            databaseTrips.child(tripId).setValue(trip);
        }
        return tripId;

    }

    public void updateTrip(Trip trip) {
        databaseTrips.child(trip.getTripID()).setValue(trip);

    }


    public void deleteTrip(String tripId) {
        databaseTrips.child(tripId).removeValue();
    }

    public void checkAuthentication(String email, String password, final Activity activity, final LoginContract.PresenterInterface presenterInterface) {

        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
// If sign in fails, display a message to the user. If sign in succeeds
// the auth state listener will be notified and logic to handle the
// signed in user can be handled in the listener.

                        if (!task.isSuccessful()) {
                            presenterInterface.loginSuccess(false);
                        } else {
                            saveUserID();
                            presenterInterface.loginSuccess(true);
                        }
                    }
                });
    }

    public void registerNew(String email, String password, final RegisterContract.PresenterInterface presenterInterface) {
        FirebaseApp.initializeApp(GlobalApplication.getAppContext());
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            saveUserID();
                            presenterInterface.registerSuccess(true);
                        } else {
                            presenterInterface.registerSuccess(false);

                        }

                    }
                });
    }

    public void logout() {
        FirebaseAuth.getInstance().signOut();
    }

    private void saveUserID() {
        String user_id = auth.getCurrentUser().getUid();
        if (user_id != null) {
            SavedPreferences.getInstance().writeUserID(user_id);
        }

    }

    public int getLastRequestID() {
        int requestID = 0;
        if (!trips.isEmpty()) {
            requestID = trips.get(trips.size() - 1).getRequestId();
        }
        return requestID;
    }

}



