package com.andro.itrip.mainActivity;


import com.andro.itrip.User;

public class MainContract {
    public interface PresenterInterface {
       void logout();
       void getUser();

    }

    public interface ViewInterface {
        void logoutSuccessful();
        void updateUserData(User user) ;
    }

}
