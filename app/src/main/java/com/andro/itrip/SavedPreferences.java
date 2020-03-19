package com.andro.itrip;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

public class SavedPreferences {
    private static SavedPreferences instance;
    public static final String USER_DATA = "userData";
    public static final String USER_ID = "userID";
    public static final String USER_EMAIL = "userEmail";
    public static final String USER_NAME = "username";
    public static final String FIRSTCREATE = "FIRSTCREATE";


    private SavedPreferences(){}

    public static SavedPreferences getInstance() {
        if (instance == null) {
            synchronized (SavedPreferences.class) {
                if (instance == null) {
                    instance = new SavedPreferences();
                }
            }
        }
        return instance;
    }




    public void writeUserID(String userID) {
        SharedPreferences settings = GlobalApplication.getAppContext().getSharedPreferences(USER_DATA, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(USER_ID, userID);
        editor.commit();
    }

    public String readUserID () {
        SharedPreferences settings = GlobalApplication.getAppContext().getSharedPreferences(USER_DATA, 0);
       return settings.getString(USER_ID, "");
    }
    public void resetSavedPreference(){
      writeUserID("");
      writeLoginEmailandUsername("","");
      writeFirstCreate(false);
    }

    public void writeLoginEmailandUsername(String email, String username){
        SharedPreferences settings = GlobalApplication.getAppContext().getSharedPreferences(USER_DATA, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(USER_EMAIL, email);
        editor.putString(USER_NAME, username);
        editor.commit();
    }
    public String[] readLoginEmail(){
        String[] arr = new String[2];
        SharedPreferences settings = GlobalApplication.getAppContext().getSharedPreferences(USER_DATA, 0);
        arr[0] = settings.getString(USER_EMAIL, "");
        arr[1] = settings.getString(USER_NAME, "");
        return arr;
    }



    public void writeFirstCreate(boolean isCreated){
        SharedPreferences settings = GlobalApplication.getAppContext().getSharedPreferences(USER_DATA, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean(FIRSTCREATE, isCreated);
        editor.commit();
    }
    public boolean readFirstCreate(){
        SharedPreferences settings = GlobalApplication.getAppContext().getSharedPreferences(USER_DATA, 0);
        return settings.getBoolean(FIRSTCREATE,false);
    }



}
