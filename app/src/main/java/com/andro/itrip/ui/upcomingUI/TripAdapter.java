package com.andro.itrip.ui.upcomingUI;

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
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.List;

public class TripAdapter extends RecyclerView.Adapter<TripAdapter.ViewHolder> {
    private List<Trip> tripData;
    private UpcomingContract.PresenterInterface presenterInterface;
    private Context context;


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

    public TripAdapter(List<Trip> tripData, UpcomingContract.PresenterInterface presenterInterface, Context context) {
        this.tripData = tripData;
        this.presenterInterface = presenterInterface;
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
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        setAlarmForTrips(tripData);
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

                if (tripData.get(position).getRepeat().equals(context.getString(R.string.once))) {
                    tripData.get(position).setIsRepeated(false);
                    tripData.get(position).setStatus(Utils.STATUS_DONE);
                    presenterInterface.onUpdate(tripData.get(position));

                } else if (tripData.get(position).getRepeat().equals(context.getString(R.string.daily))) {
                   changeDateForRepeatTrips(1,position);

                } else {
                    changeDateForRepeatTrips(7,position);
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
        });
        holder.cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tripData.get(position).setStatus(0);
                presenterInterface.onUpdate(tripData.get(position));
                presenterInterface.getTripList();
                AlarmManagerHandler.getInstance().cancelAlarm(tripData.get(position));
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

    private void setAlarmForTrips(List<Trip> tripList) {
        if (!SavedPreferences.getInstance().readFirstCreate()) {
            SavedPreferences.getInstance().writeFirstCreate(true);
            if (tripList != null && !tripList.isEmpty()) {
                for (int index = 0; index < tripList.size(); index++) {
                    Calendar calStart = HelpingMethods.convertToDate(tripList.get(index).getStartDateTime());
                    HelpingMethods.convertToDate(tripList.get(index).getStartDateTime());
                    AlarmManagerHandler.getInstance().setAlarmManager(calStart, tripList.get(index), tripList.get(index).getRequestId());
                    if (tripList.get(index).getTripType().equals(GlobalApplication.getAppContext().getString(R.string.round_trip))) {
                        Calendar calRound = HelpingMethods.convertToDate(tripList.get(index).getStartDateTime());
                        AlarmManagerHandler.getInstance().setAlarmManager(calRound, tripList.get(index), tripList.get(index).getRequestId() + 1);
                    }
                }
            }
        }
    }
    private void changeDateForRepeatTrips(int numbersOfDays , int position){

        if (!tripData.get(position).isRepeated()) {
            Trip oldTrip = new Trip(tripData.get(position));
            oldTrip.setStatus(Utils.STATUS_DONE);
            presenterInterface.addTrip(oldTrip);
        }
        Calendar cal = HelpingMethods.convertToDate(tripData.get(position).getStartDateTime());
        cal.add(Calendar.DATE, numbersOfDays);
        String dateString = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT).format(cal.getTime());
        tripData.get(position).setStartDateTime(dateString);
        tripData.get(position).setIsRepeated(true);
        presenterInterface.onUpdate(tripData.get(position));

    }


    }

