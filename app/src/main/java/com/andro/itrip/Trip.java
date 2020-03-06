package com.andro.itrip;

public class Trip {
    private String tripID;
    private String tripTitle;
    private String startDateTime;
    private String status;
    private String isRounded;
    private String repeat;

    private String startLocation;
    private String startLat;
    private String startLang;

    private String destinationLocation;
    private String destinationLat;
    private String destinationLang;

    public Trip(){

    }

    public Trip(String tripID, String tripTitle, String startDateTime, String status, String isRounded, String repeat, String startLocation, String startLat, String startLang, String destinationLocation, String destinationLat, String destinationLang) {
        this.tripID=tripID;
        this.tripTitle=tripTitle;
        this.startDateTime=startDateTime;
        this.status=status;
        this.isRounded=isRounded;
        this.repeat=repeat;
        this.startLocation=startLocation;
        this.startLat=startLat;
        this.startLang=startLang;
        this.destinationLocation=destinationLocation;
        this.destinationLat=destinationLat;
        this.destinationLang=destinationLang;
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

    public String getStatus() {
        return status;
    }

    public String getIsRounded() {
        return isRounded;
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

    public void setTripID(String tripID) {
        this.tripID=tripID;
    }

    public void setTripTitle(String tripTitle) {
        this.tripTitle=tripTitle;
    }

    public void setStartDateTime(String startDateTime) {
        this.startDateTime=startDateTime;
    }


    public void setStatus(String status) {
        this.status=status;
    }

    public void setIsRounded(String isRounded) {
        this.isRounded=isRounded;
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
}