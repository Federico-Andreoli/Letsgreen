package it.unimib.lets_green.ui.dashboard;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
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

        //creazione dell'elemento per prendere parametri passati da 'Cat1Fragment'
        Bundle bundle = this.getArguments();

        TextView plantName;
        TextView plantCommonName;
        TextView plantDescription;
        TextView co2Absorption;

        plantName = view.findViewById(R.id.plantName);
        plantCommonName = view.findViewById(R.id.plantCommonName);
        plantDescription = view.findViewById(R.id.plantDescription);
        co2Absorption = view.findViewById(R.id.co2Absorption);

        plantName.setText(bundle.getString("name").substring(0, 1).toUpperCase() + bundle.getString("name").substring(1).toLowerCase());
        plantCommonName.setText(bundle.getString("common_name"));
        plantDescription.setText(bundle.getString("description"));
        co2Absorption.setText("Co2 absorption: " + bundle.getString("co2_absorption"));

        ImageView imageView1;
        // ImageView imageView2; vedere se tenere due immagini o ridurre ad una

        imageView1 = view.findViewById(R.id.image1);
        //imageView2 = view.findViewById(R.id.image2);

        // scaricamento e settaggio immagine
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference gsReference = storage.getReferenceFromUrl("gs://lets-green-b9ddf.appspot.com/" + bundle.getString("name") + ".png");

        final long ONE_MEGABYTE = 1024 * 1024; // dimensione massima dell'immagine da scaricare
        gsReference.getBytes(ONE_MEGABYTE).addOnSuccessListener(bytes -> {
            Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            imageView1.setImageBitmap(bmp);
        }).addOnFailureListener(exception -> {
            // TODO: Handle any errors
        });

        /*
        TODO: da eliminare quando sono state settate per bene le altre immagini
        (anche ora non serve più)
        imageView1.setImageResource(R.drawable.citta_inquinata);
        imageView2.setImageResource(R.drawable.foresta1);
        */

        // settaggio bottone per aggiunta alla serra
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