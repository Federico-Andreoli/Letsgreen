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
                (tab, position) -> tab.setText(setTabName(position))
        ).attach();
    }

    public String setTabName(int position) {
        switch (position) {
            case 0:
                return "trees";
            case 1:
                return "grass";
            case 2:
                return "leaves";
            default:
                return "cat not found";
        }
    }

}

class DemoCollectionAdapter extends FragmentStateAdapter {

    public DemoCollectionAdapter(Fragment fragment, int tabsNum) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        /*
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
        non serve più: è stato sostituito dall'istruzione sotto in modo da caricare sempre lo stesso
        fragment ma caricarlo in base ai dati richiesti
        (da finire implementazione nella classe del fragment)
         */

        return new Cat1Fragment(position);
    }

    @Override
    public int getItemCount() {
        return 3;
    }

}