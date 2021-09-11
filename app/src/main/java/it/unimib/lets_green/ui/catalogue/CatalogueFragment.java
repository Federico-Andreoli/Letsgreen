package it.unimib.lets_green.ui.catalogue;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import it.unimib.lets_green.MainActivity;
import it.unimib.lets_green.R;

public class CatalogueFragment extends Fragment {

    DemoCollectionAdapter demoCollectionAdapter;
    private ViewPager2 viewPager;

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_catalogue, container, false);
        ((MainActivity) getActivity()).setActionBarTitle(getString(R.string.catalogue));
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        demoCollectionAdapter = new DemoCollectionAdapter(this, 3);
        viewPager = view.findViewById(R.id.view_pager);
        viewPager.setAdapter(demoCollectionAdapter);
        FragmentStateAdapter pagerAdapter = new DemoCollectionAdapter(this, 3);
        TabLayout tabLayout = view.findViewById(R.id.tabs);

        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                Navigation.findNavController(view).navigate(R.id.navigation_home);
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(getActivity(), callback);

        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> tab.setText(setTabName(position))
        ).attach();
    }



    public String setTabName(int position) {
        switch (position) {
            case 0:
                return "trees";
            case 1:
                return "ferns";
            case 2:
                return "mosses";
            default:
                return "not found";
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
        return new CatFragment(position);
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}