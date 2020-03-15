package com.andro.itrip;

import android.content.Intent;
import android.os.Bundle;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import com.andro.itrip.R;
import com.andro.itrip.loginActivity.LoginActivity;
import com.andro.itrip.mainActivity.MainActivity;
import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntroFragment;

public class AppIntroActivity extends AppIntro {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_app_intro);

        addSlide(AppIntroFragment.newInstance("Plan your trips.", "Daily planner, to manage your trips.",
                R.drawable.first, ContextCompat.getColor(getApplicationContext(), R.color.palette2)));
        addSlide(AppIntroFragment.newInstance("Get Notification", "Suit up. It's time for fun.",
                R.drawable.second, ContextCompat.getColor(getApplicationContext(), R.color.palette3)));
        addSlide(AppIntroFragment.newInstance("Start your trip.", "Step by step, till your destination!",
                R.drawable.third, ContextCompat.getColor(getApplicationContext(), R.color.palette2)));
    }


    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        Intent intent=new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        Intent intent=new Intent(getApplicationContext(),LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
