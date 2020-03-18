package com.andro.itrip.dialogActivity;

import com.andro.itrip.FireBaseHandler;
import com.andro.itrip.Trip;

import java.util.List;

public class DialogPresenter implements DialogContract.PresenterInterface {
    private DialogContract.ViewInterface viewInterface;

    public DialogPresenter(DialogContract.ViewInterface viewInterface) {
        this.viewInterface = viewInterface;
    }

    @Override
    public void onUpdate(Trip trip) {
        FireBaseHandler.getInstance().updateTrip(trip);
    }



}
