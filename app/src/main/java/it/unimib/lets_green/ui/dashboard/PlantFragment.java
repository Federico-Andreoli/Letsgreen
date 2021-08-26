package it.unimib.lets_green.ui.dashboard;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import it.unimib.lets_green.R;

public class PlantFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_plant, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = this.getArguments();

        TextView nomePianta;
        TextView nomeComunePianta;

        nomePianta = view.findViewById(R.id.plantName);
        nomeComunePianta = view.findViewById(R.id.plantCommonName);

        nomePianta.setText(bundle.getString("name"));
        nomeComunePianta.setText(bundle.getString("common_name"));

        ImageView imageView1;
        ImageView imageView2;

        imageView1 = view.findViewById(R.id.image1);
        imageView2 = view.findViewById(R.id.image2);

        imageView1.setImageResource(R.drawable.citta_inquinata);
        imageView2.setImageResource(R.drawable.foresta1);

        FloatingActionButton floatingActionButton = view.findViewById(R.id.floating_action_button);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(view, "Hai premuto aggiungi", Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();
            }
        });
        }

}