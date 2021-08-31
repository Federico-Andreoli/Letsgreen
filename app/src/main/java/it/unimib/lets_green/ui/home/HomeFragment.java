package it.unimib.lets_green.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import it.unimib.lets_green.R;

public class HomeFragment extends Fragment {
    private TextView textCO2emessa;
    private TextView textCO2assorbita;
    private HomeViewModel homeViewModel;
    private CardView carbonCard;
    private String CO2emessa;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        carbonCard = root.findViewById(R.id.cardViewPollution);
        textCO2emessa = root.findViewById(R.id.textView4);
        textCO2assorbita= root.findViewById(R.id.textView7);
        carbonCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(root).navigate(R.id.pathFragment);
            }
        });

        textCO2emessa.setText("1234");
        textCO2assorbita.setText("123456");

        return root;
    }
}