package com.andro.itrip;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Trip implements Parcelable {
    private String tripID;
    private String tripTitle;
    private String startDateTime;
    //private String status;
    private int status = Utils.STATUS_UPCOMING;
    private String tripType;
    private String repeat;
    private String roundDateTime;

    private String startLocation;
    private String startLat;
    private String startLang;



    private String destinationLocation;
    private String destinationLat;
    private String destinationLang;
    private ArrayList<String> notesList;
    private static int increment = FireBaseHandler.getInstance().getLastRequestID();
    private int requestId;
    public Trip(){
        requestId = ++increment + 2;

    }


    public Trip(String tripTitle, String startDateTime, int status, String tripType, String repeat, String roundDateTime, String startLocation, String startLat, String startLang, String destinationLocation, String destinationLat, String destinationLang,ArrayList<String> notesList) {
        this.tripTitle = tripTitle;
        this.startDateTime = startDateTime;
        this.status = status;
        this.tripType = tripType;
        this.repeat = repeat;
        this.roundDateTime = roundDateTime;
        this.startLocation = startLocation;
        this.startLat = startLat;
        this.startLang = startLang;
        this.destinationLocation = destinationLocation;
        this.destinationLat = destinationLat;
        this.destinationLang = destinationLang;
        this.notesList = notesList;



    }


    protected Trip(Parcel in) {
        tripID = in.readString();
        tripTitle = in.readString();
        startDateTime = in.readString();
        status = in.readInt();
        tripType = in.readString();
        repeat = in.readString();
        roundDateTime = in.readString();
        startLocation = in.readString();
        startLat = in.readString();
        startLang = in.readString();
        destinationLocation = in.readString();
        destinationLat = in.readString();
        destinationLang = in.readString();
        notesList = in.createStringArrayList();
        requestId = in.readInt();
    }

    public static final Creator<Trip> CREATOR = new Creator<Trip>() {
        @Override
        public Trip createFromParcel(Parcel in) {
            return new Trip(in);
        }

        @Override
        public Trip[] newArray(int size) {
            return new Trip[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(tripID);
        parcel.writeString(tripTitle);
        parcel.writeString(startDateTime);
        parcel.writeInt(status);
        parcel.writeString(tripType);
        parcel.writeString(repeat);
        parcel.writeString(roundDateTime);
        parcel.writeString(startLocation);
        parcel.writeString(startLat);
        parcel.writeString(startLang);
        parcel.writeString(destinationLocation);
        parcel.writeString(destinationLat);
        parcel.writeString(destinationLang);
        parcel.writeStringList(notesList);
        parcel.writeInt(requestId);
    }

    public String getTripID() {
        return tripID;
    }

    public String getTripTitle() {
        return tripTitle;
    }

    public String getStartDateTime() {
        return startDateTime;
    }

    public int getStatus() {
        return status;
    }

    public String getTripType() {
        return tripType;
    }

    public String getRepeat() {
        return repeat;
    }

    public String getStartLocation() {
        return startLocation;
    }

    public String getStartLat() {
        return startLat;
    }

    public String getStartLang() {
        return startLang;
    }

    public String getDestinationLocation() {
        return destinationLocation;
    }

    public String getDestinationLat() {
        return destinationLat;
    }

    public String getDestinationLang() {
        return destinationLang;
    }

    public String getRoundDateTime() {
        return roundDateTime;
    }

    public ArrayList<String> getNotesList() {
        return notesList;
    }

    public int getRequestId() {
        return requestId;
    }

    public void setTripID(String tripID) {
        this.tripID=tripID;
    }

    public void setTripTitle(String tripTitle) {
        this.tripTitle=tripTitle;
    }

    public void setStartDateTime(String startDateTime) {
        this.startDateTime=startDateTime;
    }


    public void setStatus(int status) {
        this.status=status;
    }

    public void setTripType(String tripType) {
        this.tripType = tripType;
    }

    public void setRepeat(String repeat) {
        this.repeat=repeat;
    }

    public void setStartLocation(String startLocation) {
        this.startLocation=startLocation;
    }

    public void setStartLat(String startLat) {
        this.startLat=startLat;
    }

    public void setStartLang(String startLang) {
        this.startLang=startLang;
    }

    public void setDestinationLocation(String destinationLocation) {
        this.destinationLocation=destinationLocation;
    }

    public void setDestinationLat(String destinationLat) {
        this.destinationLat=destinationLat;
    }

    public void setDestinationLang(String destinationLang) {
        this.destinationLang=destinationLang;
    }

    public void setRoundDateTime(String roundDateTime) {
        this.roundDateTime = roundDateTime;
    }


    public void setNotesList(ArrayList<String> notesList) {
        this.notesList = notesList;
    }


}