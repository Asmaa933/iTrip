package com.andro.itrip.registerActivity;

import android.app.Activity;

public class RegisterContract {
    public interface PresenterInterface {
        void registerNewAccount(String email, String password);
        void registerSuccess(boolean isRegister);

    }

    public  interface ViewInterface {
        void displayError(int message);
        void redirectMainScreen();
    }
}
