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
import android.widget.Toast;

import com.andro.itrip.addTripActivity.AddTripActivity;
import com.andro.itrip.R;
import com.andro.itrip.ui.historyUI.HistoryFragment;
import com.andro.itrip.ui.loginActivity.LoginActivity;
import com.andro.itrip.ui.upcomingUI.UpcomingFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

     private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab=findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //test
                Intent intent = new Intent(MainActivity.this, AddTripActivity.class);
                //intent.putExtra("userId",user_id);
                startActivity(intent);
                finish();
                /*
                Trip tr = new Trip("First","Mar 6, 2020 07:33 PM","upcomming","true","true","cairo" ,
                "33","34","ismailia","43","45");
        FireBaseHandler.getInstance().addTrip(tr);
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
            }
        });
        final DrawerLayout drawer=findViewById(R.id.drawer_layout);



        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawer,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView=findViewById(R.id.nav_view);

        UpcomingFragment upcomingFragment=new UpcomingFragment();
        getSupportFragmentManager().beginTransaction().replace( R.id.nav_host_fragment,upcomingFragment).commit();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFregment=null;
                switch (item.getItemId()) {
                    case R.id.nav_logout:
                        logout();
                        Toast.makeText(MainActivity.this,"Log Out",Toast.LENGTH_LONG).show();
                        break;
                    case  R.id.nav_upcoming:
                        selectedFregment=new UpcomingFragment();
                        getSupportFragmentManager().beginTransaction().replace( R.id.nav_host_fragment,selectedFregment).commit();
                        break;
                    case R.id.nav_history:
                        selectedFregment=new HistoryFragment() ;
                        getSupportFragmentManager().beginTransaction().replace( R.id.nav_host_fragment,selectedFregment).commit();
                        break;
                }
                drawer.closeDrawers();
                return true;

            }
        });

    }

    private void logout() {
        FirebaseAuth.getInstance().signOut();
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
        NavController navController=Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}
