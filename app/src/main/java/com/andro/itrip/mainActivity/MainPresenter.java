package com.andro.itrip.mainActivity;

import com.andro.itrip.FireBaseHandler;

public class MainPresenter implements MainContract.PresenterInterface {
    private MainContract.ViewInterface viewInterface;

    public MainPresenter(MainContract.ViewInterface viewInterface) {
        this.viewInterface = viewInterface;
    }

    @Override
    public void logout() {
        FireBaseHandler.getInstance().logout();
        viewInterface.logoutSuccessful();
    }


}
