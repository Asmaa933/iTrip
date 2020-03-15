package com.andro.itrip;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.andro.itrip.loginActivity.LoginActivity;
import com.andro.itrip.mainActivity.MainActivity;
import com.github.paolorotolo.appintro.AppIntro;

import gr.net.maroulis.library.EasySplashScreen;

public class SplashScreenActivity extends AppCompatActivity {


    EasySplashScreen config;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();

        boolean chechIntro = pref.getBoolean("has_watch_intro", true); // getting boolean

        editor.putBoolean("has_watch_intro", false); // Storing boolean - true/false
        editor.commit(); // commit changes



        if(chechIntro){
            // True -----> first time
            config = new EasySplashScreen(SplashScreenActivity.this)
                    .withFullScreen()
                    .withTargetActivity(AppIntroActivity.class)
                    .withSplashTimeOut(3000)
                    .withBackgroundColor(Color.parseColor("#1a1b29"))
                    .withAfterLogoText("First time to run!")
                    .withLogo(R.mipmap.ic_launcher_round);
        }else {
            // True -----> Not first time
            config = new EasySplashScreen(SplashScreenActivity.this)
                    .withFullScreen()
                    .withTargetActivity(LoginActivity.class)
                    .withSplashTimeOut(3000)
                    .withBackgroundColor(Color.parseColor("#00ffaa"))
                    .withAfterLogoText("Welcome Back Yasta!")
                    .withLogo(R.mipmap.ic_launcher_round);
        }

        config.getAfterLogoTextView().setTextColor(Color.WHITE);

        View easySplashScreen = config.create();
        setContentView(easySplashScreen);
    }
}
