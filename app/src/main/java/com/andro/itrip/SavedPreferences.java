package com.andro.itrip;

import android.content.Context;
import android.content.SharedPreferences;

public class SavedPreferences {
    private static SavedPreferences instance;
    public static final String USER_DATA = "userData";
    public static final String USER_ID = "userID";
    private Context context;

    private SavedPreferences(Context context){
        this.context =  context;
    }
    private SavedPreferences() {
    }
    public static SavedPreferences getInstance(Context context) {

        if (instance == null) {
            synchronized (SavedPreferences.class) {
                if (instance == null) {
                    instance = new SavedPreferences(context);
                }
            }
        }
        return instance;
    }

    public static SavedPreferences getInstance(){
            return instance;
    }


    public void writeUserID(String userID) {
        SharedPreferences settings = context.getSharedPreferences(USER_DATA, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(USER_ID, userID);
        editor.commit();
    }

    public String readUserID () {
        SharedPreferences settings = context.getSharedPreferences(USER_DATA, 0);
       return settings.getString(USER_ID, "Not Found");
    }

}
