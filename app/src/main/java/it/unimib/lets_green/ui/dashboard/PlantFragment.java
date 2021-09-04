package it.unimib.lets_green.ui.dashboard;

import static it.unimib.lets_green.Login.getIs_logged;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import it.unimib.lets_green.DialogFragment;
import it.unimib.lets_green.FirestoreDatabase.FirestoreDatabase;
import it.unimib.lets_green.R;

public class PlantFragment extends Fragment {

    private SwipeRefreshLayout refreshLayout;
    private TextView plantName;
    private TextView plantCommonName;
    private TextView plantDescription;
    private TextView co2Absorption;
    private Bundle bundle;
    private ImageView imageView1;
    private ProgressBar progressBar;
    private StorageReference gsReference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_plant, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //creazione dell'elemento per prendere parametri passati da 'Cat1Fragment'
        bundle = this.getArguments();

        plantName = view.findViewById(R.id.plantName);
        plantCommonName = view.findViewById(R.id.plantCommonName);
        plantDescription = view.findViewById(R.id.plantDescription);
        co2Absorption = view.findViewById(R.id.co2Absorption);
        imageView1 = view.findViewById(R.id.image1);
        progressBar = view.findViewById(R.id.image_progress_bar);

        plantName.setText(bundle.getString("name").substring(0, 1).toUpperCase() + bundle.getString("name").substring(1).toLowerCase());
        plantCommonName.setText(bundle.getString("common_name"));
        plantDescription.setText(bundle.getString("description"));
        co2Absorption.setText("Co2 absorption: " + bundle.getString("co2_absorption"));

        // settaggio refresh della pagina
        refreshLayout = view.findViewById(R.id.plant_refresh_layout);
        refreshLayout.setColorSchemeResources(R.color.green);
        refreshLayout.setOnRefreshListener(() -> {
            Navigation.findNavController(view).navigate(R.id.plantFragment, bundle);
        });

        // scaricamento e settaggio immagine
        gsReference = FirebaseStorage.getInstance().getReferenceFromUrl("gs://lets-green-b9ddf.appspot.com/" + bundle.getString("name") + ".png");

        final long ONE_MEGABYTE = 1024 * 1024; // dimensione massima dell'immagine da scaricare
        gsReference.getBytes(ONE_MEGABYTE).addOnSuccessListener(bytes -> {
            Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            imageView1.setImageBitmap(bmp);
            progressBar.setVisibility(View.GONE);

        }).addOnFailureListener(exception -> {
            // TODO: Handle any errors
        });

        // settaggio bottone per aggiunta alla serra
        FloatingActionButton floatingActionButton = view.findViewById(R.id.floating_action_button);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!getIs_logged()) {
                    DialogFragment dialogFragment = new DialogFragment();
                    dialogFragment.show(getActivity().getSupportFragmentManager(), "example");
                } else {
                    FirestoreDatabase.addPlantToGreenHouse(bundle.getString("name"), bundle.getString("co2_absorption"));
                    Toast.makeText(getActivity(), "plant added to greenhouse", Toast.LENGTH_SHORT).show();
                }
                }
        });

    }

}