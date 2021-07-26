package it.unimib.lets_green;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import it.unimib.lets_green.ui.dashboard.DashboardFragment;
import it.unimib.lets_green.ui.home.HomeFragment;
import it.unimib.lets_green.ui.notifications.NotificationsFragment;

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
        //NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        mToolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(mToolbar);

        /* inserire la selezione del fragment in base al bottone premuto */

        if(savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.scene_root, new HomeFragment()).commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.top_bar_menu, menu);
        return true;
    }

    /*
    private final BottomNavigationView.OnNavigationItemSelectedListener listener= new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected( MenuItem item) {
            Fragment selected_fragment =null;
            switch (item.getItemId()){

                case R.id.:
                    selected_fragment =HomeFragment.newInstance();
                    break;

                case R.id.:
                    selected_fragment = DashboardFragment.newInstance();
                    break;

                case R.id.settings:
                    selected_fragment = NotificationsFragment.newInstance();
                    break;
            }
            if (selected_fragment != null) {
                FragmentManager fm= getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.scene_root, selected_fragment);
                ft.addToBackStack("attività");
                ft.commit();

            }
            return true;
        }
    };

     */
}