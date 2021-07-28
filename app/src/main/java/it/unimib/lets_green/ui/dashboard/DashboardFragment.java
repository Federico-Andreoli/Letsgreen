package it.unimib.lets_green.ui.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import org.jetbrains.annotations.NotNull;

import it.unimib.lets_green.Cat1Fragment;
import it.unimib.lets_green.Cat2Fragment;
import it.unimib.lets_green.Cat3Fragment;
import it.unimib.lets_green.MainActivity;
import it.unimib.lets_green.R;

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;

    private ViewPager2 viewPager;
    private TabLayout tabLayout;

    private Cat1Fragment cat1Fragment;
    private Cat2Fragment cat2Fragment;
    private Cat3Fragment cat3Fragment;

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        /*
        startActivity(new Intent(getActivity(),MainActivity.class));

        cat1Fragment = new Cat1Fragment();
        cat2Fragment = new Cat2Fragment();
        cat3Fragment = new Cat3Fragment();

        viewPager = ((MainActivity)getActivity()).findViewById(R.id.view_pager);
        tabLayout = ((MainActivity)getActivity()).findViewById(R.id.tabs);

        viewPager.setAdapter(new ViewPagerAdapter(this));

        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            switch (position) {
                case 0:
                    tab.setText("cat1");
                    break;
                case 1:
                    tab.setText("cat2");
                    break;
                case 2:
                    tab.setText("cat3");
                    break;
            }
        }).attach(); */

        //tabLayout.setupWithViewPager(viewPager);

        //ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(((MainActivity)getActivity()).getSupportFragmentManager());
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        return root;
    }

    /*private class ViewPagerAdapter extends FragmentStateAdapter {

        public ViewPagerAdapter(@NonNull @NotNull DashboardFragment dashboardFragment) {
            super(dashboardFragment);
        }

        @NonNull
        @NotNull
        @Override
        public Fragment createFragment(int position) {
            switch (position) {
                case 0:
                    return new Cat1Fragment();
                case 1:
                    return new Cat2Fragment();
                case 2:
                    return new Cat3Fragment();
                default:
                    return null;
            }
        }

        @Override
        public int getItemCount() {
            return 3;
        }
    }*/
}