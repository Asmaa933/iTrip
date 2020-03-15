package com.andro.itrip.ui.historyUI;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.andro.itrip.GlobalApplication;
import com.andro.itrip.R;
import com.andro.itrip.Trip;
import com.andro.itrip.addTripActivity.AddTripActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class HistoryTripAdapter extends RecyclerView.Adapter<HistoryTripAdapter.ViewHolder> {
    private List<Trip> tripData;
    private HistoryContract.PresenterInterface presenterInterface;
    private Context context;

    public class ViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout recyclerRow;
        private ImageView mapImage;
        private ImageView statusImage;
        private Button deleteButton;
        private TextView tripTitle;
        private TextView tripTime;
        private TextView fromTxtView;
        private TextView toTxtView;

        public ViewHolder(View view) {
            super(view);
            recyclerRow = view.findViewById(R.id.history_row);
            mapImage = view.findViewById(R.id.img_list_item_map_history);
            statusImage = view.findViewById(R.id.img_list_item_main_histroy);
            deleteButton = view.findViewById(R.id.delete_list_item_main_history);
            tripTitle = view.findViewById(R.id.title_list_item_histoy);
            tripTime = view.findViewById(R.id.time_list_item_history);
            fromTxtView = view.findViewById(R.id.fromTxtView);
            toTxtView = view.findViewById(R.id.toTxtView);
    }
}
        public HistoryTripAdapter(List<Trip> tripData, HistoryContract.PresenterInterface presenterInterface, Context context) {
            this.tripData = tripData;
            this.presenterInterface = presenterInterface;
            this.context = context;
        }

        @NonNull
        @Override
        public HistoryTripAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View listItem = layoutInflater.inflate(R.layout.history_custom_cell, parent, false);
            HistoryTripAdapter.ViewHolder viewHolder = new HistoryTripAdapter.ViewHolder(listItem);

            return viewHolder;
        }


    @Override
        public void onBindViewHolder(@NonNull final HistoryTripAdapter.ViewHolder holder, final int position) {
            String imgURL = "https://maps.googleapis.com/maps/api/staticmap?size=500x250" +
                    "&markers=color:blue|label:S|" + tripData.get(position).getStartLat() + "," + tripData.get(position).getStartLang() + "&markers=color:red|label:E|" + tripData.get(position).getDestinationLat() + "," + tripData.get(position).getDestinationLang() + "&key=AIzaSyDIJ9XX2ZvRKCJcFRrl-lRanEtFUow4piM";
            Picasso.get()
                    .load(imgURL)
                    .placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.ic_launcher_background)
                    .into(holder.mapImage);
            holder.tripTitle.setText(tripData.get(position).getTripTitle());
            holder.tripTime.setText(tripData.get(position).getStartDateTime());
            holder.toTxtView.setText("To : " + tripData.get(position).getDestinationLocation());
            holder.fromTxtView.setText("From : " + tripData.get(position).getStartLocation());

        switch (tripData.get(position).getStatus()){
                case 0:
                    holder.statusImage.setImageResource(R.drawable.cancel);
                    break;
                case 1:
                    holder.statusImage.setImageResource(R.drawable.done);
                    break;
                case 2:
                    holder.statusImage.setImageResource(R.drawable.upcoming);
                    break;
            }

            holder.deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showDeleteAlert(position);
                }
            });
        }

        @Override
        public int getItemCount() {
            return tripData.size();
        }

    private void showDeleteAlert(final int position){
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
