package com.andro.itrip.ui.upcomingUI;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
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
    private UpcomingContract.PresenterInterface presenterInterface;


    public class ViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout recyclerRow;
        private ImageView mapImage;
        private ImageView statusImage;
        private TextView statusText;
        private Button deleteButton;
        private Button notesButton;
        private TextView tripTitle;
        private TextView tripTime;
        private ImageButton startButton;


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

    public TripAdapter(List<Trip> tripData, Context context, UpcomingContract.PresenterInterface presenterInterface) {
        this.tripData = tripData;
        this.context = context;
        this.presenterInterface = presenterInterface;
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
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.tripTitle.setText(tripData.get(position).getTripTitle());
        holder.tripTime.setText(tripData.get(position).getStartDateTime());
        holder.statusText.setText(tripData.get(position).getStatus());
        final int positionOfTrip = position;

        holder.startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//check update
                Trip trip = new Trip("updateddd","Mar 6, 2020 07:33 PM","upcomming","true","true","cairo" ,
             "33","34","ismailia","43","45");
                trip.setTripID(tripData.get(positionOfTrip).getTripID());
                presenterInterface.onUpdate(trip);

            }
        });
        holder.notesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
           showDeleteAlert(positionOfTrip);
            }
        });
        holder.recyclerRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return tripData.size();
    }

    public void showDeleteAlert(final int position){
        AlertDialog.Builder Builder = new AlertDialog.Builder(context)
                .setMessage(R.string.delete_trip)
                .setCancelable(false)
                .setPositiveButton(R.string.card_delete, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        presenterInterface.onDelete(tripData.get(position).getTripID());
                      presenterInterface.getTripList();

                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();

                    }
                });

        AlertDialog alertDialog = Builder.create();
        alertDialog.show();
    }
}
