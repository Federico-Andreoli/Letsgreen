package it.unimib.lets_green;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.work.Constraints;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import it.unimib.lets_green.WorkManager.UploadWorker;

public class MainActivity extends AppCompatActivity {

    private Toolbar mToolbar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.bottom_navigation);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        /*
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        tolta perchè entrava in conflitto con la top app bar personalizzata -lori
         */
        NavigationUI.setupWithNavController(navView, navController);

        mToolbar = findViewById(R.id.topAppBar);
        setSupportActionBar(mToolbar);



    }


     public boolean onOptionsItemSelected( MenuItem item) {
            int id = item.getItemId();
            if (id == R.id.action_profile) {

                NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
                navController.navigate(R.id.fragment_login);
                return true;
            }

            return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        if (Login.getIs_logged()){
            Constraints constraints= new Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .build();

            OneTimeWorkRequest uploadDataRequest=new OneTimeWorkRequest.Builder(UploadWorker.class).setConstraints(constraints).build();
            WorkManager.getInstance(this).enqueue(uploadDataRequest);
        }
        super.onPause();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.top_bar_menu, menu);
        return true;
    }

    public Activity getActivityReference(){

        return this;
    }
}