package it.unimib.lets_green.ui.dashboard;

import static it.unimib.lets_green.FirestoreDatabase.FirestoreDatabase.TAG;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import java.util.ArrayList;
import java.util.List;
import it.unimib.lets_green.R;

public class Cat1Fragment extends Fragment {

    private List<Plant> plants = new ArrayList<Plant>();
    private int position; // usato più in basso

    public Cat1Fragment(int position) {
        this.position = position;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cat1, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        // TODO: fare la query in base a position per prendere dinamicamente la categoria giusta

        // chiamata al database per estrarre tutte le piante
        FirebaseFirestore.getInstance().collection("plants").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    Log.d(TAG, document.getId() + " => " + document.getData());
                    plants.add(new Plant(document.getId(), document.getData().get("common_name").toString(), document.getData().get("species").toString()));
                }
            } else {
                Log.d(TAG, "Error getting documents: ", task.getException());
            }

            RecyclerView recyclerView = view.findViewById(R.id.cat1_view);
            CatalogueRecyclerViewAdapter catalogRecyclerViewAdapter = new CatalogueRecyclerViewAdapter(getContext(), plants, position -> {
                Toast.makeText(getActivity(), "elemento " + position, Toast.LENGTH_SHORT).show();
                // creazione dell'elemento che conterrà i paramentri da passare
                Bundle bundle = new Bundle();
                // inserimento dei parametri all'interno del bundle
                bundle.putString("name", plants.get(position).getName());
                bundle.putString("common_name", plants.get(position).getCommonName());
                bundle.putString("species", plants.get(position).getSpecies());
                // passaggio all'altro fragment
                Navigation.findNavController(view).navigate(R.id.plantFragment, bundle);
            }
            );
            recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
            recyclerView.setAdapter(catalogRecyclerViewAdapter);
        });
    }

}