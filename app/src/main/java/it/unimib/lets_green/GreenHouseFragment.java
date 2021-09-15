package it.unimib.lets_green;

import static it.unimib.lets_green.FirestoreDatabase.FirestoreDatabase.TAG;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import it.unimib.lets_green.ui.Login.Login;


public class GreenHouseFragment extends Fragment {
    private FirebaseFirestore firebaseFirestore;
    private RecyclerView mRecyclerView;
    private GreenHouseAdapter mAdapter;
    private ArrayList<GreenHouseItem> plantList = new ArrayList<>();

    public GreenHouseFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_green_house, container, false);

        mRecyclerView = view.findViewById(R.id.RecyclerView);

        // impostazione titolo action bar
        ((MainActivity) requireActivity()).setActionBarTitle(getString(R.string.greenHouse));

        // impostazione della transizione in entrata per questo fragment
        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {// gestisce il pulsante di back
            @Override
            public void handleOnBackPressed() {
                Navigation.findNavController(view).navigate(R.id.navigation_home);
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(getActivity(), callback);

        setUpRecyclerViewGreenHouse();
        Float score = GreenHouseFragmentArgs.fromBundle(getArguments()).getScoreHp();
        Log.d(TAG, score.toString());

        if (score != 0) {
            firebaseFirestore = FirebaseFirestore.getInstance();
            firebaseFirestore.collection("User").document(Login.getUserID()).collection("greenHouse").get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                int count = 0;
                                for (DocumentSnapshot document : task.getResult()) {
                                    count++;
                                }
                                Float singlePlantScore = score / count;
                                firebaseFirestore.collection("User").document(Login.getUserID()).collection("greenHouse").get()
                                        .addOnCompleteListener(task1 -> {
                                            if (task1.isSuccessful()) {
                                                for (QueryDocumentSnapshot documentSnapshot : task1.getResult()) {
                                                    firebaseFirestore.collection("User").document(Login.getUserID()).collection("greenHouse")
                                                            .document(documentSnapshot.getId()).get().addOnCompleteListener(task2 -> {
                                                        if (task2.isSuccessful()) {
                                                            DocumentSnapshot documentSnapshot1 = task2.getResult();
                                                            if (documentSnapshot1.exists()) {
                                                                Log.d(TAG, documentSnapshot1.getString("hp"));
                                                                Log.d(TAG, singlePlantScore.toString());
                                                                Float updatedSinglePlantScore = Float.parseFloat(documentSnapshot1.getString("hp")) - singlePlantScore;
                                                                Log.d(TAG, updatedSinglePlantScore.toString());
                                                                firebaseFirestore.collection("User").document(Login.getUserID()).collection("greenHouse")
                                                                        .document(documentSnapshot1.getId()).update("hp", String.valueOf(updatedSinglePlantScore));
                                                            }
                                                        }
                                                    });
                                                }
                                            }
                                        });
                            } else {
                                Log.d(TAG, "Error getting documents: ", task.getException());
                            }
                        }
                    });
            setUpRecyclerViewGreenHouse();
        }

        return view;
    }

    @Override
    public void onStop() {
        super.onStop();
        mAdapter.stopListening();
    }

    @Override
    public void onStart() {
        super.onStart();
        mAdapter.startListening();
    }

    public void createGreenHouse(View view) {
        setUpRecyclerViewGreenHouse();
    }
//collegamento con il database
    public void setUpRecyclerViewGreenHouse(){
        firebaseFirestore = FirebaseFirestore.getInstance();
        Query query = firebaseFirestore.collection("User").document(Login.getUserID()).collection("greenHouse");
        FirestoreRecyclerOptions<GreenHouseItem> options= new FirestoreRecyclerOptions.Builder<GreenHouseItem>().setQuery(query, GreenHouseItem.class).build();

        mRecyclerView.setHasFixedSize(true);
        mAdapter = new GreenHouseAdapter(options,plantList);

        mAdapter.setOnItemClickListener(new GreenHouseAdapter.onItemClickListener(){
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                GreenHouseItem itemPlant = documentSnapshot.toObject(GreenHouseItem.class);
                String documentID = documentSnapshot.getId();
            }
        });

        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        mRecyclerView.setAdapter(mAdapter);
    }
}
