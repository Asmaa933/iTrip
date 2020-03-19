package com.andro.itrip.mainActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.andro.itrip.FireBaseHandler;
import com.andro.itrip.GlobalApplication;
import com.andro.itrip.HelpingMethods;
import com.andro.itrip.SavedPreferences;
import com.andro.itrip.Trip;
import com.andro.itrip.User;
import com.andro.itrip.addTripActivity.AddTripActivity;
import com.andro.itrip.R;
import com.andro.itrip.loginActivity.LoginContract;
import com.andro.itrip.ui.historyUI.HistoryFragment;
import com.andro.itrip.loginActivity.LoginActivity;
import com.andro.itrip.ui.upcomingUI.UpcomingFragment;
import com.andro.itrip.ui.upcomingUI.UpcomingPresenter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements MainContract.ViewInterface {
    MainContract.PresenterInterface mainPresenter;
    private AppBarConfiguration mAppBarConfiguration;
    private TextView emailTxt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainPresenter = new MainPresenter(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getString(R.string.upcoming));


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (HelpingMethods.isNetworkConnected()) {
                    Intent intent = new Intent(MainActivity.this, AddTripActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(GlobalApplication.getAppContext(), getString(R.string.check_internet), Toast.LENGTH_LONG).show();
                }


            }
        });
        final DrawerLayout drawer = findViewById(R.id.drawer_layout);
      ;
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);

        TextView navUseremail = (TextView) headerView.findViewById(R.id.txtemail);
        TextView navUsername = (TextView) headerView.findViewById(R.id.txtName);

        String[] userdata = SavedPreferences.getInstance().readLoginEmail();

        navUseremail.setText(userdata[0]);
        navUsername.setText(userdata[1]);

        UpcomingFragment upcomingFragment = new UpcomingFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, upcomingFragment).commit();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;
                switch (item.getItemId()) {
                    case R.id.nav_logout:
                        mainPresenter.logout();
                        Toast.makeText(MainActivity.this, getString(R.string.logout), Toast.LENGTH_LONG).show();
                        break;
                    case R.id.nav_upcoming:
                        getSupportActionBar().setTitle(getString(R.string.upcoming));
                        selectedFragment = new UpcomingFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, selectedFragment).commit();
                        break;
                    case R.id.nav_history:
                        getSupportActionBar().setTitle(getString(R.string.history));

                        selectedFragment = new HistoryFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, selectedFragment).commit();
                        break;
                }
                drawer.closeDrawers();
                return true;

            }
        });

    }

    public void logoutSuccessful() {
        finish();
        startActivity(new Intent(this, LoginActivity.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main2, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}
