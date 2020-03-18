package com.andro.itrip.headUI;

import android.content.Context;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.andro.itrip.R;
import com.andro.itrip.addTripActivity.NotesAdapter;

import java.util.ArrayList;
import java.util.HashMap;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class HeadNotesAdapter extends RecyclerView.Adapter<HeadNotesAdapter.NoteViewHandler> {
    private ArrayList<String> notesList;
    private Context context;
    ChatHeadService headService;

    public class NoteViewHandler extends RecyclerView.ViewHolder {

        CheckBox checkBox = new CheckBox(context);

        public NoteViewHandler(View itemView) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.check);
        }
    }

    public HeadNotesAdapter(ArrayList<String> notesList, ChatHeadService headService, Context context) {
        this.notesList = notesList;
        this.context = context;
        this.headService = headService;
    }

    @NonNull
    @Override
    public NoteViewHandler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater itemView = LayoutInflater.from(parent.getContext());
        View listItem = itemView.inflate(R.layout.note_item_notification, parent, false);
        NoteViewHandler noteViewHandler = new NoteViewHandler(listItem);
        return noteViewHandler;
    }

    @Override
    public void onBindViewHolder(@NonNull HeadNotesAdapter.NoteViewHandler holder, int position) {

        holder.checkBox.setText(notesList.get(position).toString());
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return notesList.size();
    }
}
