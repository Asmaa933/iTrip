package com.andro.itrip.ui.upcomingUI;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.andro.itrip.R;
import com.andro.itrip.Trip;

import java.util.List;

public class TripAdapter extends RecyclerView.Adapter<TripAdapter.ViewHolder> {
    private Context context;
    private List<Trip> tripData;

    public class ViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout recyclerRow;
        private ImageView mapImage;
        private ImageView statusImage;
        private TextView statusText;
        private Button deleteButton;
        private Button notesButton;
        private TextView tripTitle;
        private TextView tripTime;
        private Button startButton;


        public ViewHolder(View view) {
            super(view);
            recyclerRow = view.findViewById(R.id.row);
            mapImage = view.findViewById(R.id.img_list_item_map);
            statusImage = view.findViewById(R.id.img_list_item_main);
            statusText = view.findViewById(R.id.status_list_item_main);
            deleteButton = view.findViewById(R.id.delete_list_item_main);
            notesButton = view.findViewById(R.id.note_list_item_main);
            tripTitle = view.findViewById(R.id.title_list_item_main);
            tripTime = view.findViewById(R.id.time_list_item_main);
            startButton = view.findViewById(R.id.start_list_item_main);
        }
    }

    public TripAdapter(List<Trip> tripData , Context context) {
        this.tripData = tripData;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.list_item_main, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tripTitle.setText(tripData.get(position).getTripTitle());
        holder.tripTime.setText(tripData.get(position).getStartDateTime());
        holder.statusText.setText(tripData.get(position).getStatus());

        holder.startButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

            }
        });
        holder.notesButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

            }
        });
        holder.deleteButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

            }
        });
        holder.recyclerRow.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return tripData.size();
    }



}
