package it.unimib.lets_green.ui.home;

import static it.unimib.lets_green.Login.getIs_logged;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import it.unimib.lets_green.DialogFragment;
import it.unimib.lets_green.R;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private CardView carbonCard;
    private CardView greenHouseCard;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        carbonCard = root.findViewById(R.id.cardViewPollution);
        greenHouseCard = root.findViewById(R.id.cardViewPlant);
        greenHouseCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!getIs_logged()) {
                DialogFragment dialogFragment = new DialogFragment();
                dialogFragment.show(getActivity().getSupportFragmentManager(), "example");
                } else {
                    Navigation.findNavController(root).navigate(R.id.greenHouseFragment);
                }
            }
        });
        carbonCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!getIs_logged()) {
                    DialogFragment dialogFragment = new DialogFragment();
                    dialogFragment.show(getActivity().getSupportFragmentManager(), "example");
                } else {
                    Navigation.findNavController(root).navigate(R.id.pathFragment);
                }
            }
        });
        return root;
    }
}