package com.andro.itrip.loginActivity;

import android.app.Activity;

public class LoginContract {
    public interface PresenterInterface {
        void verifyCredentials(String email, String password, Activity activity);
        void loginSuccess(boolean isSuccess);

    }

    public  interface ViewInterface {
        void displayError(int message);
        void loginSuccessful();
    }
}
