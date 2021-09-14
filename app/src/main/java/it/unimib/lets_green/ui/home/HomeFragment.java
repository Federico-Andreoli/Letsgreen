package it.unimib.lets_green.ui.home;

import static it.unimib.lets_green.ui.Login.Login.getIs_logged;
import static it.unimib.lets_green.FirestoreDatabase.FirestoreDatabase.TAG;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

import it.unimib.lets_green.DialogFragment;
import it.unimib.lets_green.FirestoreDatabase.FirestoreDatabase;
import it.unimib.lets_green.GreenHouseItem;
import it.unimib.lets_green.MainActivity;
import it.unimib.lets_green.R;
import it.unimib.lets_green.ui.Login.Login;

public class HomeFragment extends Fragment {

    private CardView carbonCard;
    private CardView greenHouseCard;
    private double totalHp = 0.0;
    private LocalDate lastUpdate = LocalDate.ofEpochDay(1970-01-01);
    private RecyclerView scoreView;
    private ScoreRecyclerViewAdapter scoreRecyclerViewAdapter;
    private Task<QuerySnapshot> collectionReference;
    private FirebaseFirestore firebaseFirestore;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);

        carbonCard = root.findViewById(R.id.cardViewPollution);
        greenHouseCard = root.findViewById(R.id.cardViewPlant);

        scoreView = root.findViewById(R.id.scoreView);

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

        if(getIs_logged() && totalHp == 0.0) {
            // recupero la data dell'ultimo update
            FirebaseFirestore.getInstance()
                    .collection("User")
                    .document(Login.getUserID())
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                lastUpdate = LocalDate.parse(document.get("lastUpdate").toString());
                            } else {
                                Log.d(TAG, "no such document");
                            }
                        } else {
                            Log.d(TAG, "get failed with", task.getException());
                        }
                        Log.d(TAG, "last update: " + lastUpdate.toString());
                        // creazione recycler view e aggiornamento hp se necessario
                        if (needUpdate(lastUpdate)) {
                            hpGreenHouseSinglePlantReset();
                            hpUpdate(root);
//                            metodo che resetta la vita
                        }
                        else
                            createRecyclerView(root);
                    });
        }

        return root;
    }

    @Override
    public void onStart() {

        super.onStart();
        ((MainActivity) requireActivity()).setActionBarTitle("Home");

    }

    public void hpGreenHouseSinglePlantReset(){
        firebaseFirestore = FirebaseFirestore.getInstance();
        collectionReference = firebaseFirestore.collection("User").document(Login.getUserID()).collection("greenHouse").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
//                    List<GreenHouseItem> greenHouseItemList = new ArrayList<>();
                    for(QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                        firebaseFirestore.collection("User").document(Login.getUserID()).collection("greenHouse").document(documentSnapshot.getId())
                                .set(firebaseFirestore.collection("plants").document(documentSnapshot.getString("namePlant"))
                                        .get().addOnCompleteListener(task1 -> {
                                            if (task1.isSuccessful()) {
                                                DocumentSnapshot documentSnapshot1 = task1.getResult();
                                                if (documentSnapshot1.exists()) {
                                                    documentSnapshot1.getString("co2_absortion");
                                                } else {
                                                    Log.d(TAG, "no such document");
                                                }
                                            } else {
                                                Log.d(TAG, "get failed with", task1.getException());
                                            }
                                        }));
                    }
                }else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }
            }
        });
    }

    public void hpUpdate(View root) {
        FirebaseFirestore.getInstance()
                .collection("User")
                .document(Login.getUserID())
                .collection("greenHouse")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null) {
                            Log.w(TAG, "Listen failed.", error);
                            return;
                        }
                        for(DocumentSnapshot document: value) {
                            if(document.get("hp") != null)
                                totalHp += Double.parseDouble(document.getString("hp"));
                        }
                        createRecyclerView(root);
                    }
                });
    }

    public void createRecyclerView(View root) {
        scoreView = root.findViewById(R.id.scoreView);
        // aggiorno i valori di hp e la data nel database
        FirestoreDatabase.updateHp(totalHp);
        FirestoreDatabase.updateDate();
        scoreRecyclerViewAdapter = new ScoreRecyclerViewAdapter(getContext(), totalHp);
        scoreView.setLayoutManager(new LinearLayoutManager(getActivity()));
        scoreView.setAdapter(scoreRecyclerViewAdapter);
    }

    public LocalDate getDate() {
        return LocalDate.now();
    }

    public boolean needUpdate(LocalDate lastUpdate) {
        if (Period.between(lastUpdate, getDate()).getDays() >= 1)
            return true;
        else
            return false;
    }
}