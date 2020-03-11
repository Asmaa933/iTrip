package com.andro.itrip.addTripActivity;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;


import com.andro.itrip.AlertReceiver;
import com.andro.itrip.GlobalApplication;
import com.andro.itrip.R;
import com.andro.itrip.Trip;
import com.andro.itrip.mainActivity.MainActivity;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;


import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

import java.text.DateFormat;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class AddTripActivity extends AppCompatActivity implements AddTripContract.ViewInterface {

    private EditText editTxtTripName, editTxtAddNote;
    private Spinner spinnerTripType, spinnerTripRepetition;
    private TextView singleTxtViewDateTime, roundTxtViewDateTime;
    private Button btnAddTrip;
    private ImageView imageViewAddNote;
    private LinearLayout roundTripTimeAndDate;

    private static final String apiKey = "AIzaSyBEx1-Id2GOqasqHC-2WhxTkSe2hEWZuOo";
    PlacesClient placesClient;

    private Trip trip;
    private boolean isRound = false;

    private ArrayList<String> notesArrayList;
    private NotesAdapter notesAdapter;
    private ListView notesList;
    private AddTripContract.PresenterInterface addPresenter;
    Calendar chosenSingleDate = Calendar.getInstance();
    Calendar chosenRoundDate = Calendar.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tip);

        notesList = findViewById(R.id.list_view_notes);
        editTxtTripName = findViewById(R.id.editTxt_trip_name);
        editTxtAddNote = findViewById(R.id.textView_add_note);
        spinnerTripType = findViewById(R.id.spinner_type_trip);
        spinnerTripRepetition = findViewById(R.id.spinner_trip_repetition);
        singleTxtViewDateTime = findViewById(R.id.txtView_time);
        roundTxtViewDateTime = findViewById(R.id.txtView_time2);
        btnAddTrip = findViewById(R.id.btn_add_trip);
        imageViewAddNote = findViewById(R.id.image_add_note);
        roundTripTimeAndDate = findViewById(R.id.round_trip_layout);

        addPresenter = new AddTripPresenter(this);
        notesArrayList = new ArrayList<>();
        notesAdapter = new NotesAdapter(notesArrayList, this);
        notesList.setAdapter(notesAdapter);
        trip = new Trip();

        // Setup upper toolbar with title and back button
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.add_trip));

        btnAddTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //test
                // Trip tr = new Trip("هالو","Mar 6, 2020 07:33 PM","upcomming","true","true","cairo" ,
                // "33","34","ismailia","43","45");
                ////validation
                trip.setTripTitle(editTxtTripName.getText().toString());
                trip.setStatus(getString(R.string.upcoming));
                addPresenter.addTrip(trip);
                Intent intent = new Intent(AddTripActivity.this, MainActivity.class);
                startActivity(intent);
                finish();

            }
        });

        singleTxtViewDateTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateTimePickerDialog("single");


            }
        });

        roundTxtViewDateTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateTimePickerDialog("round");

            }
        });

        spinnerTripType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String tripType = adapterView.getItemAtPosition(i).toString();
                trip.setTripType(tripType);
                if (tripType.equals("Round Trip")) {
                    isRound = true;
                    roundTripTimeAndDate.setVisibility(View.VISIBLE);
                } else
                    roundTripTimeAndDate.setVisibility(View.GONE);
                isRound = false;

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinnerTripRepetition.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String tripRepetition = adapterView.getItemAtPosition(i).toString();
                trip.setRepeat(tripRepetition);
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
        autocompleteSupportFragmentStart.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG, Place.Field.ADDRESS));
        autocompleteSupportFragmentEnd.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG, Place.Field.ADDRESS));
        autocompleteSupportFragmentStart.setOnPlaceSelectedListener(
                new PlaceSelectionListener() {
                    @Override
                    public void onPlaceSelected(Place place) {
                        final LatLng latLng = place.getLatLng();
                        trip.setStartLat(latLng.latitude + "");
                        trip.setStartLang(latLng.longitude + "");
                        trip.setStartLocation(place.getName());
                        Toast.makeText(GlobalApplication.getAppContext(), place.getName(), Toast.LENGTH_LONG).show();

                    }

                    @Override
                    public void onError(Status status) {
                        Toast.makeText(GlobalApplication.getAppContext(), "" + status.getStatusMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
        autocompleteSupportFragmentEnd.setOnPlaceSelectedListener(
                new PlaceSelectionListener() {
                    @Override
                    public void onPlaceSelected(Place place) {
                        final LatLng latLng = place.getLatLng();
                        trip.setDestinationLat(latLng.latitude + "");
                        trip.setDestinationLang(latLng.longitude + "");
                        trip.setDestinationLocation(place.getName());
                    }

                    @Override
                    public void onError(Status status) {
                        Toast.makeText(GlobalApplication.getAppContext(), "" + status.getStatusMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }


    void showDateTimePickerDialog(final String tripDirection) {
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        int mHour = c.get(Calendar.HOUR_OF_DAY);
        int mMinute = c.get(Calendar.MINUTE);
        // Launch Time Picker Dialog
        final TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {
                        if (tripDirection.equals("single")) {
                            chosenSingleDate.set(Calendar.HOUR_OF_DAY, hourOfDay);
                            chosenSingleDate.set(Calendar.MINUTE, minute);
                            chosenSingleDate.set(Calendar.SECOND, 0);
                            String singleDateTime = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT).format(chosenSingleDate.getTime());
                            singleTxtViewDateTime.setText(singleDateTime);
                            trip.setStartDateTime(singleDateTime);
                            setAlarmManager(chosenSingleDate);
                        } else {
                            chosenRoundDate.set(Calendar.HOUR_OF_DAY, hourOfDay);
                            chosenRoundDate.set(Calendar.MINUTE, minute);
                            chosenRoundDate.set(Calendar.SECOND, 0);
                            String roundDateTime = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT).format(chosenSingleDate.getTime());
                            roundTxtViewDateTime.setText(roundDateTime);
                            trip.setRoundDateTime(roundDateTime);
                        }

                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();

        timePickerDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                if (which == DialogInterface.BUTTON_NEGATIVE) {
                    Toast.makeText(getApplicationContext(), "choose time", Toast.LENGTH_LONG).show();
                }

            }
        });


        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        if (tripDirection.equals("single")) {
                            chosenSingleDate.set(Calendar.YEAR, year);
                            chosenSingleDate.set(Calendar.MONTH, monthOfYear);
                            chosenSingleDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        } else {
                            chosenRoundDate.set(Calendar.YEAR, year);
                            chosenRoundDate.set(Calendar.MONTH, monthOfYear);
                            chosenRoundDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                        }


                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                if (which == DialogInterface.BUTTON_NEGATIVE) {
                    Toast.makeText(getApplicationContext(), "choose date", Toast.LENGTH_LONG).show();
                    timePickerDialog.dismiss();
                }
            }
        });
        datePickerDialog.show();

    }

    private void setAlarmManager(Calendar date) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(GlobalApplication.getAppContext(), AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(GlobalApplication.getAppContext(), 1, intent, 0);
        if (date.before(Calendar.getInstance())) {
            date.add(Calendar.DATE, 1);
        }
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, date.getTimeInMillis(), pendingIntent);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}