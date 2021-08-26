package it.unimib.lets_green.ui.dashboard;

import static it.unimib.lets_green.FirestoreDatabase.FirestoreDatabase.TAG;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import it.unimib.lets_green.R;

public class Cat1Fragment extends Fragment {

    private List<Plant> plants = new ArrayList<Plant>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cat1, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        Task<QuerySnapshot> docRef = FirebaseFirestore.getInstance().collection("plants").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Log.d(TAG, document.getId() + " => " + document.getData());
                        plants.add(new Plant(document.getId().toString(), document.getData().get("common_name").toString(), document.getData().get("species").toString()));
                    }
                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }

                RecyclerView recyclerView = view.findViewById(R.id.cat1_view);
                CatalogueRecyclerViewAdapter catalogRecyclerViewAdapter = new CatalogueRecyclerViewAdapter(getContext(), plants, new CatalogueRecyclerViewAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        Toast.makeText(getActivity(), "elemento" + String.valueOf(position), Toast.LENGTH_SHORT).show();
                        // create bundle
                        Bundle bundle = new Bundle();
                        // send all the arguments
                        bundle.putString("name", plants.get(position).getName());
                        bundle.putString("common_name", plants.get(position).getCommonName());
                        bundle.putString("species", plants.get(position).getSpecies());
                        // change fragment
                        Navigation.findNavController(view).navigate(R.id.plantFragment, bundle);
                    }
                }
                );
                recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
                recyclerView.setAdapter(catalogRecyclerViewAdapter);
            }
        });
        }

}