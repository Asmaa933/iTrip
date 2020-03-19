package com.andro.itrip.registerActivity;

import android.app.Activity;

import com.andro.itrip.User;

public class RegisterContract {

    public interface PresenterInterface {
        void registerNewAccount(String email, String password);
        void registerSuccess(boolean isRegister);
        void addUser(User user);
    }

    public  interface ViewInterface {
        void displayError(int message);
        void redirectMainScreen();
    }
}
