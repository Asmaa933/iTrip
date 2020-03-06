package com.andro.itrip.ui.upcomingUI;

public class UpcomingContract {
    interface PresenterInterface {
        void sendMessage();
    }

    interface ViewInterface {
        void displayMessage(String message);
    }
}
