package it.unimib.lets_green.ui.catalogue;

import static it.unimib.lets_green.FirestoreDatabase.FirestoreDatabase.TAG;

import android.os.Bundle;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

import it.unimib.lets_green.R;

public class CatFragment extends Fragment {

    private List<Plant> plants = null;
    private int position;
    private SwipeRefreshLayout refreshLayout;

    public CatFragment(int position) {
        this.position = position;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cat, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        // settaggio della variabile realativa alla categoria per prendere le piante giuste
        // dal database

        String category;
        switch (position) {
            case 0:
                category = "tree";
                break;
            case 1:
                category = "evergreen";
                break;
            case 2:
                category = "fruit";
                break;
            default:
                category = "not found";
        }

        // settaggio refresh della pagina
        refreshLayout = view.findViewById(R.id.refresh_layout);
        refreshLayout.setColorSchemeResources(R.color.green);
        refreshLayout.setOnRefreshListener(() -> {
            callDatabase(category, view);
            refreshLayout.setRefreshing(false);
        });

        // chiamata al database per estrarre le piante relative alla categoria selezionata
        if(plants == null) {
            callDatabase(category, view);
        }
        else {
            createRecycleView(view);
        }
    }

    public void createRecycleView (@NonNull View view) {
        RecyclerView recyclerView = view.findViewById(R.id.cat_view);
        CatalogueRecyclerViewAdapter catalogRecyclerViewAdapter = new CatalogueRecyclerViewAdapter(getContext(), plants, position -> {
            // creazione dell'elemento che conterrà i paramentri da passare
            Bundle bundle = new Bundle();
            // inserimento dei parametri all'interno del bundle
            bundle.putString("name", plants.get(position).getName());
            bundle.putString("common_name", plants.get(position).getCommonName());
            bundle.putString("species", plants.get(position).getSpecies());
            bundle.putString("description", plants.get(position).getDescription());
            bundle.putString("co2_absorption", plants.get(position).getCo2Absorption());
            // passaggio al fragment della singola pianta
            Navigation.findNavController(view).navigate(R.id.plantFragment, bundle);
        });
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        recyclerView.setAdapter(catalogRecyclerViewAdapter);
    }

    public void callDatabase (String category, View view) {
        plants = new ArrayList<>();
        FirebaseFirestore.getInstance().collection("plants").whereEqualTo("species", category).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    //Log.d(TAG, document.getId() + " => " + document.getData());
                    plants.add(new Plant(document.getId(), document.getData().get("common_name").toString(),
                            document.getData().get("species").toString(),
                            document.getData().get("description").toString(),
                            document.getData().get("co2_absorption").toString()));
                }
            } else {
                Log.d(TAG, "Error getting documents: ", task.getException());
            }
            // metodo per la creazione della recycler view e il passaggio degli elementi al
            // fragment della singola pianta
            createRecycleView(view);
        });
    }
}