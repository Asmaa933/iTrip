package com.andro.itrip.ui.upcomingUI;

import android.app.Activity;
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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.andro.itrip.AlarmManagerHandler;
import com.andro.itrip.GlobalApplication;
import com.andro.itrip.HelpingMethods;
import com.andro.itrip.R;
import com.andro.itrip.SavedPreferences;
import com.andro.itrip.Trip;
import com.andro.itrip.Utils;
import com.andro.itrip.addTripActivity.AddTripActivity;
import com.andro.itrip.headUI.ChatHeadService;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.List;

public class TripAdapter extends RecyclerView.Adapter<TripAdapter.ViewHolder> {
    private List<Trip> tripData;
    private UpcomingContract.PresenterInterface presenterInterface;
    private Context context;
    private Activity activity;


    public class ViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout recyclerRow;
        private ImageView mapImage;
        private ImageView statusImage;
        private TextView repeatTxt;
        private Button deleteButton;
        private Button cancelButton;
        private TextView tripTitle;
        private TextView tripTime;
        private ImageButton startButton;


        public ViewHolder(View view) {
            super(view);
            recyclerRow = view.findViewById(R.id.row);
            mapImage = view.findViewById(R.id.img_list_item_map);
            statusImage = view.findViewById(R.id.img_list_item_main);
            repeatTxt = view.findViewById(R.id.status_list_item_main);
            deleteButton = view.findViewById(R.id.delete_list_item_main);
            cancelButton = view.findViewById(R.id.cancel_list_item_main);
            tripTitle = view.findViewById(R.id.title_list_item_main);
            tripTime = view.findViewById(R.id.time_list_item_main);
            startButton = view.findViewById(R.id.start_list_item_main);
        }
    }

    public TripAdapter(List<Trip> tripData, UpcomingContract.PresenterInterface presenterInterface, Context context,Activity activity) {
        this.tripData = tripData;
        this.presenterInterface = presenterInterface;
        this.context = context;
        this.activity = activity;
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
        String imgURL = "https://maps.googleapis.com/maps/api/staticmap?size=500x250" +
                "&markers=color:blue|label:S|" + tripData.get(position).getStartLat() + "," + tripData.get(position).getStartLang() + "&markers=color:red|label:E|" + tripData.get(position).getDestinationLat() + "," + tripData.get(position).getDestinationLang() + "&key=AIzaSyDIJ9XX2ZvRKCJcFRrl-lRanEtFUow4piM";
        Picasso.get()
                .load(imgURL)
                .placeholder(R.drawable.loadingindicator2)
                .error(R.drawable.imagenotfound)
                .into(holder.mapImage);
        holder.tripTitle.setText(tripData.get(position).getTripTitle());
        holder.tripTime.setText(tripData.get(position).getStartDateTime());

        if (tripData.get(position).getRepeat().equals(context.getString(R.string.once))) {
            holder.repeatTxt.setText(R.string.once);

        } else if (tripData.get(position).getRepeat().equals(context.getString(R.string.daily))) {
            holder.repeatTxt.setText(R.string.daily);
        } else {
            holder.repeatTxt.setText(R.string.weekly);
        }


        holder.startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Utils.checkPermissions()) {
                    if (Utils.isLocationEnabled()) {
                        startButtonPressed(position);
                    } else {
                        Toast.makeText(GlobalApplication.getAppContext(), "Turn on location", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Utils.requestPermissions(activity);
                }


            }
        });
        holder.cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCancelAlert(position);
            }
        });
        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDeleteAlert(position);
            }
        });
        holder.recyclerRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GlobalApplication.getAppContext(), AddTripActivity.class);
                intent.putExtra("selected trip", tripData.get(position));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setAction(GlobalApplication.getAppContext().getString(R.string.edit_trip));
                GlobalApplication.getAppContext().startActivity(intent);

            }
        });
        holder.statusImage.setImageResource(R.mipmap.ic_upcoming_foreground);
    }

    @Override
    public int getItemCount() {
        return tripData.size();
    }

    private void showDeleteAlert(final int position) {
        AlertDialog.Builder Builder = new AlertDialog.Builder(context)
                .setMessage(R.string.delete_trip)
                .setCancelable(false)
                .setPositiveButton(R.string.card_delete, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (tripData.get(position).getHistotyTripID() != null) {
                            presenterInterface.onDelete(tripData.get(position).getHistotyTripID());
                        }
                        presenterInterface.onDelete(tripData.get(position).getTripID());

                        presenterInterface.getTripList();
                        AlarmManagerHandler.getInstance().cancelAlarm(tripData.get(position));
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

    private void showCancelAlert(final int position) {
        AlertDialog.Builder Builder = new AlertDialog.Builder(context)
                .setMessage(R.string.cancel_trip_alert)
                .setCancelable(false)
                .setNegativeButton(R.string.cancel_once, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (tripData.get(position).getRepeat().equals(context.getString(R.string.once))) {
                            tripData.get(position).setTripAddedBefore(null);
                            tripData.get(position).setStatus(Utils.STATUS_CANCELLED);
                            presenterInterface.onUpdate(tripData.get(position));

                        } else if (tripData.get(position).getRepeat().equals(context.getString(R.string.daily))) {
                            changeDateForRepeatTrips(1, position, Utils.STATUS_CANCELLED);


                        } else {
                            changeDateForRepeatTrips(7, position, Utils.STATUS_CANCELLED);
                        }
                        presenterInterface.getTripList();
                        AlarmManagerHandler.getInstance().cancelAlarm(tripData.get(position));

                    }
                })

                .setNeutralButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();

                    }
                })

                .setPositiveButton(R.string.cancel_repeat, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        tripData.get(position).setStatus(0);
                        presenterInterface.onUpdate(tripData.get(position));
                        presenterInterface.getTripList();
                        AlarmManagerHandler.getInstance().cancelAlarm(tripData.get(position));
                        if (tripData.get(position).getHistotyTripID() != null) {
                            presenterInterface.onDelete(tripData.get(position).getHistotyTripID());
                        }
                    }
                });


        AlertDialog alertDialog = Builder.create();
        alertDialog.show();
    }

    private void changeDateForRepeatTrips(int numbersOfDays, int position, int status) {

        if (tripData.get(position).getTripAddedBefore() == null || tripData.get(position).getTripAddedBefore().isEmpty()) {
            Trip oldTrip = new Trip(tripData.get(position));
            oldTrip.setStatus(status);
            presenterInterface.addTrip(oldTrip);
            tripData.get(position).setHistotyTripID(oldTrip.getTripID());

        }
        String dateString = HelpingMethods.increaseDays(numbersOfDays, tripData.get(position).getStartDateTime());
        tripData.get(position).setStartDateTime(dateString);
        tripData.get(position).setTripAddedBefore("yes");
        presenterInterface.onUpdate(tripData.get(position));


    }
private void startButtonPressed(int position){
    if (tripData.get(position).getRepeat().equals(context.getString(R.string.once))) {
        tripData.get(position).setTripAddedBefore(null);
        tripData.get(position).setStatus(Utils.STATUS_DONE);
        presenterInterface.onUpdate(tripData.get(position));

    } else if (tripData.get(position).getRepeat().equals(context.getString(R.string.daily))) {
        changeDateForRepeatTrips(1, position, Utils.STATUS_DONE);

    } else {
        changeDateForRepeatTrips(7, position, Utils.STATUS_DONE);
    }

    AlarmManagerHandler.getInstance().cancelAlarm(tripData.get(position));

    Intent headIntent = new Intent(GlobalApplication.getAppContext(), ChatHeadService.class);
    headIntent.putStringArrayListExtra("notes", tripData.get(position).getNotesList());
    GlobalApplication.getAppContext().startService(headIntent);

    double sourceLongitude = Double.parseDouble(tripData.get(position).getStartLang());

    double sourceLatitude = Double.parseDouble(tripData.get(position).getStartLat());

    double destinationLongitude = Double.parseDouble(tripData.get(position).getDestinationLang());

    double destinationLatitude = Double.parseDouble(tripData.get(position).getDestinationLat());

    String uri = "http://maps.google.com/maps?daddr=" + destinationLatitude + "," + destinationLongitude;

    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    intent.setPackage("com.google.android.apps.maps");
    GlobalApplication.getAppContext().startActivity(intent);


}

}

