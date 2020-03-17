package com.andro.itrip.headUI;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.andro.itrip.R;
import com.andro.itrip.Utils;
import com.andro.itrip.addTripActivity.NotesAdapter;

import java.util.ArrayList;

public class MyDialog extends AppCompatActivity {
    public static boolean active = false;
    public static Activity myDialog;

    private ArrayList<String> notesArrayList;
    private NotesAdapter notesAdapter;
    private ListView notesList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub

        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.dialog);
        myDialog = MyDialog.this;

        notesList = findViewById(R.id.list_view_notes);
        notesArrayList = new ArrayList<>();

        Intent incomingIntent = getIntent();
        if(incomingIntent!=null){
            notesArrayList = incomingIntent.getStringArrayListExtra("notes");
        }

        notesAdapter = new NotesAdapter(notesArrayList, this);
        notesList.setAdapter(notesAdapter);


    }


    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        active = true;
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        active = false;
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        active = false;
    }



}

