package com.andro.itrip.ui.upcomingUI;

public class UpcomingContract {
    interface PresenterInterface {
        public void sendMessage();
    }

    interface ViewInterface {
        public void displayMessage(String message);
    }
}
