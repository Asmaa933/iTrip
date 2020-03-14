package com.andro.itrip.ui.historyUI;

import com.andro.itrip.FireBaseHandler;
import com.andro.itrip.Trip;

import java.util.List;

public class HistoryPresenter implements HistoryContract.PresenterInterface {
    HistoryContract.ViewInterface viewInterface;

    public HistoryPresenter(HistoryContract.ViewInterface viewInterface) {
        this.viewInterface=viewInterface;
    }

    public void sendMessage() {
        String msg = "This is History fragment!!";
        viewInterface.displayMessage(msg);
    }

    @Override
    public void getTripList() {
    }

    @Override
    public void onDelete(String tripId) {

    }

}
