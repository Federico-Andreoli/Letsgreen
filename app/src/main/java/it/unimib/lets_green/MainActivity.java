package it.unimib.lets_green;

import android.app.Activity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.viewpager.widget.ViewPager;

import org.jetbrains.annotations.NotNull;

import it.unimib.lets_green.FirestoreDatabase.FirestoreDatabase;
import it.unimib.lets_green.ui.dashboard.DashboardFragment;
import it.unimib.lets_green.ui.home.HomeFragment;
import it.unimib.lets_green.ui.notifications.NotificationsFragment;

public class MainActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private String ciao;
    private CardView carbonCard;

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
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.top_bar_menu, menu);
        return true;
    }

    public Activity getActivityReference(){
        return this;
    }
}