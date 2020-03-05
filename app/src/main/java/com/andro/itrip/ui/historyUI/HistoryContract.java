package com.andro.itrip.ui.historyUI;

public class HistoryContract {

    interface PresenterInterface {
        public void sendMessage();
    }

    interface ViewInterface {
        public void displayMessage(String message);
    }

}
