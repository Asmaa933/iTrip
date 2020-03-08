package com.andro.itrip.loginActivity;

import android.app.Activity;

import com.andro.itrip.FireBaseHandler;
import com.andro.itrip.R;

public class LoginPresenter implements LoginContract.PresenterInterface {
    private LoginContract.ViewInterface viewInterface;

    public LoginPresenter(LoginContract.ViewInterface viewInterface) {
        this.viewInterface = viewInterface;
    }


    @Override
    public void verifyCredentials(String email, String password, Activity activity) {
         FireBaseHandler.getInstance().checkAuthentication(email, password, activity,this);

    }

    @Override
    public void loginSuccess(boolean isSuccess) {
        if (isSuccess){
            viewInterface.loginSuccessful();
        }
        else{
            viewInterface.displayError(R.string.login_failed);
        }
    }
}
