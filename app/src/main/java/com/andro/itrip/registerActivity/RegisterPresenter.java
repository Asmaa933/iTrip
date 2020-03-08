package com.andro.itrip.registerActivity;

import android.app.Activity;

import com.andro.itrip.FireBaseHandler;
import com.andro.itrip.R;

public class RegisterPresenter implements RegisterContract.PresenterInterface {
    private RegisterContract.ViewInterface viewInterface;

    public RegisterPresenter(RegisterContract.ViewInterface viewInterface) {
        this.viewInterface = viewInterface;
    }

    @Override
    public void registerNewAccount(String email, String password) {
        FireBaseHandler.getInstance().registerNew(email,password,this);
    }

    @Override
    public void registerSuccess(boolean isRegister) {
        if(isRegister){
            viewInterface.redirectMainScreen();
        }else {
            viewInterface.displayError(R.string.unable_register);
        }
    }
}
