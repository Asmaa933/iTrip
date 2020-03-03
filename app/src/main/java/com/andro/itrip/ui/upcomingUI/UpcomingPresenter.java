package com.andro.itrip.ui.upcomingUI;

public class UpcomingPresenter implements UpcomingContract.PresenterInterface{
    UpcomingContract.ViewInterface viewInterface;

    public UpcomingPresenter(UpcomingContract.ViewInterface viewInterface) {
        this.viewInterface=viewInterface;
    }

    public void sendMessage() {
        String msg = "This is Upcoming fragment!!";
        viewInterface.displayMessage(msg);
    }
}
