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

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class AddTripActivity extends AppCompatActivity implements AddTripContract.ViewInterface {

    private EditText editTxtTripName, editTxtAddNote;
    private Spinner spinnerTripType, spinnerTripRepetition;
    private TextView txtViewDateTime, txtViewDateTime2;
    private Button btnAddTrip;
    private ImageView imageViewAddNote;
    private LinearLayout roundTripTimeAndDate;

    private Trip trip;

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
        txtViewDateTime = findViewById(R.id.txtView_time);
        txtViewDateTime2 = findViewById(R.id.txtView_time2);
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
                //addPresenter.addTrip(trip);
                Intent intent = new Intent(AddTripActivity.this, MainActivity.class);
                startActivity(intent);
                finish();

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
    }

    void showDatePickerDialog(final String tripDirection) {
        //TODO
    }

    void showTimePickerDialog(final String tripDirection) {
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
                            txtViewDateTime.setText(DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT).format(chosenSingleDate.getTime()));
                            setAlarmManager(chosenSingleDate);
                        } else {
                            chosenRoundDate.set(Calendar.HOUR_OF_DAY, hourOfDay);
                            chosenRoundDate.set(Calendar.MINUTE, minute);
                            chosenRoundDate.set(Calendar.SECOND, 0);
                            txtViewDateTime2.setText(DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT).format(chosenRoundDate.getTime()));

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