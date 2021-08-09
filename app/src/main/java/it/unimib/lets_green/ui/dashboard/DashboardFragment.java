package it.unimib.lets_green.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import it.unimib.lets_green.R;

public class DashboardFragment extends Fragment {

    DemoCollectionAdapter demoCollectionAdapter;
    private ViewPager2 viewPager;

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DashboardViewModel dashboardViewModel = new ViewModelProvider(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        demoCollectionAdapter = new DemoCollectionAdapter(this, 3);
        viewPager = view.findViewById(R.id.view_pager);
        viewPager.setAdapter(demoCollectionAdapter);
        FragmentStateAdapter pagerAdapter = new DemoCollectionAdapter(this, 3);
        TabLayout tabLayout = view.findViewById(R.id.tabs);
        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> tab.setText("CAT " + (position + 1))
        ).attach();
    }

}

class DemoCollectionAdapter extends FragmentStateAdapter {

    private int tabsNum;

    public DemoCollectionAdapter(Fragment fragment, int tabsNum) {
        super(fragment);
        this.tabsNum = tabsNum;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        /*
        da eliminare --lori
        // Return a NEW fragment instance in createFragment(int)
        Fragment fragment = new DemoObjectFragment();
        Bundle args = new Bundle();
        // Our object is just an integer :-P
        args.putInt(DemoObjectFragment.ARG_OBJECT, position + 1);
        fragment.setArguments(args);
        */

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

}
