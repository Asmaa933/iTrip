package com.andro.itrip;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class AddTripActivity extends AppCompatActivity {

    EditText editTxtTripName, editTxtAddNote;
    Spinner spinnerTripType, spinnerTripRepetition;
    TextView txtViewDateTime, txtViewDateTime2;
    Button btnAddTrip;
    ImageView imageViewAddNote;
    LinearLayout roundTripTimeAndDate;


    private static final String apiKey = "AIzaSyBEx1-Id2GOqasqHC-2WhxTkSe2hEWZuOo";
    PlacesClient placesClient;

    //Trip trip;

    ArrayList<String> notesArrayList;
    NotesAdapter notesAdapter;
    ListView notesList;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tip);

        notesList = findViewById(R.id.list_view_notes);
        editTxtTripName = findViewById(R.id.editTxt_trip_name);
        editTxtAddNote = findViewById(R.id.textView_add_note);
        spinnerTripType = findViewById(R.id.spinner_type_trip);
        spinnerTripRepetition = findViewById(R.id.spinner_trip_repetition);
        txtViewDateTime = findViewById(R.id.txtView_time);
        txtViewDateTime2 = findViewById(R.id.txtView_time2);
        btnAddTrip = findViewById(R.id.btn_add_trip);
        imageViewAddNote = findViewById(R.id.image_add_note);
        roundTripTimeAndDate = findViewById(R.id.round_trip_layout);

        notesArrayList = new ArrayList<>();
        notesAdapter = new NotesAdapter(notesArrayList, this);
        notesList.setAdapter(notesAdapter);
        final String uid = user.getUid();
        //trip = new Trip();

        // Setup upper toolbar with title and back button
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.add_trip));

        btnAddTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO
            }
        });

        txtViewDateTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTimePickerDialog("single");
            }
        });

        txtViewDateTime2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTimePickerDialog("round");
            }
        });

        spinnerTripType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String tripType = adapterView.getItemAtPosition(i).toString();
                //trip.setType(tripType);
                if (tripType.equals("Round Trip")) {
                    roundTripTimeAndDate.setVisibility(View.VISIBLE);
                } else
                    roundTripTimeAndDate.setVisibility(View.GONE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinnerTripRepetition.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String tripRepetition = adapterView.getItemAtPosition(i).toString();
                //trip.setRepetition(tripRepetition);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // No thing to do here
            }

        });

        imageViewAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (editTxtAddNote.getText().toString().length() > 0) {
                    notesArrayList.add(editTxtAddNote.getText().toString());
                    editTxtAddNote.setText("");

                    notesAdapter.notifyDataSetChanged();
                }
            }
        });

        //Ramzy
        // Setup Places Client
        if (!Places.isInitialized()) {
            Places.initialize(AddTripActivity.this, apiKey);
        }
        placesClient = Places.createClient(this);
        final AutocompleteSupportFragment autocompleteSupportFragmentStart =
                (AutocompleteSupportFragment)
                        getSupportFragmentManager().findFragmentById(R.id.editTxt_start_point);
        final AutocompleteSupportFragment autocompleteSupportFragmentEnd =
                (AutocompleteSupportFragment)
                        getSupportFragmentManager().findFragmentById(R.id.editTxt_end_point);
        autocompleteSupportFragmentStart.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME,Place.Field.LAT_LNG,Place.Field.ADDRESS));
        autocompleteSupportFragmentEnd.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME,Place.Field.LAT_LNG,Place.Field.ADDRESS));
        autocompleteSupportFragmentStart.setOnPlaceSelectedListener(
                new PlaceSelectionListener() {
                    @Override
                    public void onPlaceSelected(Place place) {
                        final LatLng latLng = place.getLatLng();

                        Toast.makeText(AddTripActivity.this, ""+latLng.latitude, Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onError(Status status) {
                        Toast.makeText(AddTripActivity.this, ""+status.getStatusMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
        autocompleteSupportFragmentEnd.setOnPlaceSelectedListener(
                new PlaceSelectionListener() {
                    @Override
                    public void onPlaceSelected(Place place) {
                        final LatLng latLng = place.getLatLng();

                        Toast.makeText(AddTripActivity.this, ""+latLng.latitude, Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onError(Status status) {
                        Toast.makeText(AddTripActivity.this, ""+status.getStatusMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
        //Ramzy
    }

    void showDatePickerDialog(final String tripDirection) {
        //TODO
    }

    void showTimePickerDialog(final String tripDirection) {
        //TODO
    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}