package it.unimib.lets_green.ui.catalogue;

import static it.unimib.lets_green.FirestoreDatabase.FirestoreDatabase.TAG;
import static it.unimib.lets_green.ui.Login.Login.getIs_logged;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.OnBackPressedCallback;
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
import it.unimib.lets_green.MainActivity;
import it.unimib.lets_green.R;

public class PlantFragment extends Fragment {

    private SwipeRefreshLayout refreshLayout;
    private TextView plantName;
    private TextView plantCommonName;
    private TextView plantDescription;
    private TextView co2Absorption;
    private Bundle receiveBundle;
    private ImageView imageView1;
    private ProgressBar progressBar;
    private StorageReference gsReference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // impostazione titolo action bar
        ((MainActivity) getActivity()).setActionBarTitle(getString(R.string.plant));

        return inflater.inflate(R.layout.fragment_plant, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // impostazione della transizione in entrata per questo fragment
        TransitionInflater transitionInflater = TransitionInflater.from(requireContext());
        setEnterTransition(transitionInflater.inflateTransition(R.transition.slide_right));

        // gestione del ritorno al fragment precedente
        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                Navigation.findNavController(view).navigate(R.id.navigation_catalogue);
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(getActivity(), callback);

        // creazione dell'elemento per prendere parametri passati da 'CatFragment' e associazione
        // con i campi da visualizzare
        receiveBundle = this.getArguments();

        plantName = view.findViewById(R.id.plantName);
        plantCommonName = view.findViewById(R.id.plantCommonName);
        plantDescription = view.findViewById(R.id.plantDescription);
        co2Absorption = view.findViewById(R.id.co2Absorption);
        imageView1 = view.findViewById(R.id.image1);
        progressBar = view.findViewById(R.id.image_progress_bar);

        plantName.setText(receiveBundle.getString("name").substring(0, 1).toUpperCase() + receiveBundle.getString("name").substring(1).toLowerCase());
        plantCommonName.setText(receiveBundle.getString("common_name"));
        plantDescription.setText(receiveBundle.getString("description"));
        co2Absorption.setText(getString(R.string.dailyCo2Absorption) + " " + receiveBundle.getString("co2_absorption") + getString(R.string.grams));

        // settaggio refresh della pagina
        refreshLayout = view.findViewById(R.id.plant_refresh_layout);
        refreshLayout.setColorSchemeResources(R.color.green);
        refreshLayout.setOnRefreshListener(() -> {
            Navigation.findNavController(view).navigate(R.id.plantFragment, receiveBundle);
        });

        // scaricamento e settaggio immagine
        gsReference = FirebaseStorage.getInstance().getReferenceFromUrl("gs://lets-green-b9ddf.appspot.com/" + receiveBundle.getString("name") + ".png");
        final long ONE_MEGABYTE = 1024 * 1024; // dimensione massima dell'immagine da scaricare
        gsReference.getBytes(ONE_MEGABYTE).addOnSuccessListener(bytes -> {
            Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            imageView1.setImageBitmap(bmp);
            progressBar.setVisibility(View.GONE);
        }).addOnFailureListener(exception -> {
            Log.d(TAG, "image failure");
        });

        // settaggio bottone per aggiunta alla serra
        FloatingActionButton floatingActionButton = view.findViewById(R.id.floating_action_button);
        floatingActionButton.setOnClickListener(v -> {
            if(!getIs_logged()) {
                DialogFragment dialogFragment = new DialogFragment();
                dialogFragment.show(getActivity().getSupportFragmentManager(), "login requested");
            } else {
                FirestoreDatabase.addPlantToGreenHouse(receiveBundle.getString("name"), receiveBundle.getString("co2_absorption"), receiveBundle.getString("co2_absorption"));
                FirestoreDatabase.addPlantToScore(Double.parseDouble(receiveBundle.getString("co2_absorption")));
                Toast.makeText(getActivity(), R.string.addPlantToGreenhouse, Toast.LENGTH_SHORT).show();
            }
            });
    }

}