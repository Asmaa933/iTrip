package com.andro.itrip.dialogActivity;

import android.app.Activity;

import com.andro.itrip.Trip;

import java.util.List;

public class DialogContract {
    public interface PresenterInterface {
        void onUpdate(Trip trip);

    }

    public  interface ViewInterface {


    }
}
