package com.andro.itrip.ui.historyUI;

public class HistoryPresenter implements HistoryContract.PresenterInterface {
    HistoryContract.ViewInterface viewInterface;

    public HistoryPresenter(HistoryContract.ViewInterface viewInterface) {
        this.viewInterface=viewInterface;
    }

    public void sendMessage() {
        String msg = "This is History fragment!!";
        viewInterface.displayMessage(msg);
    }
}
