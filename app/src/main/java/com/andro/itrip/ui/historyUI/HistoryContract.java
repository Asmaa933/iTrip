package com.andro.itrip.ui.historyUI;

public class HistoryContract {

    interface PresenterInterface {
        void sendMessage();
    }

    interface ViewInterface {
        void displayMessage(String message);
    }

}
