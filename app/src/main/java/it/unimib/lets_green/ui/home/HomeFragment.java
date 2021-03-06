package it.unimib.lets_green.ui.home;

import static it.unimib.lets_green.FirestoreDatabase.FirestoreDatabase.TAG;
import static it.unimib.lets_green.ui.Login.Login.getIs_logged;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
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

import it.unimib.lets_green.DialogFragment;
import it.unimib.lets_green.FirestoreDatabase.FirestoreDatabase;
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

        // passaggio al fragment relativo alla greenhouse
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

        // passaggio al fragment relativo ai percorsi
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

        // metodo per aggiornare il punteggio a seconda delle piante possedute
        // solo se l'ultimo aggiornamento ?? avvenuto un giorno precedente
        if(getIs_logged()) {
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
                        // creazione recycler view e aggiornamento hp se necessario
                        if (needUpdate(lastUpdate)) {
                            hpGreenHouseSinglePlantReset();
                            hpUpdate(root);
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
        // impostazione titolo action bar
        ((MainActivity) requireActivity()).setActionBarTitle("Home");

    }

    // reset della vita di ogni signola pianta (visualizzata nella greenhouse)
    // tramite chiamate al database
    public void hpGreenHouseSinglePlantReset(){
        firebaseFirestore = FirebaseFirestore.getInstance();
        collectionReference = firebaseFirestore.collection("User")
                .document(Login.getUserID()).collection("greenHouse")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                        firebaseFirestore.collection("User").document(Login.getUserID()).collection("greenHouse").document(documentSnapshot.getId())
                            .get().addOnCompleteListener(task1 -> {
                                if (task1.isSuccessful()) {
                                    DocumentSnapshot documentSnapshot1 = task1.getResult();
                                    if (documentSnapshot1.exists()) {
                                        firebaseFirestore.collection("plants").document(documentSnapshot1.getString("namePlant")).get().addOnCompleteListener(task2 -> {
                                           if (task2.isSuccessful()){
                                               DocumentSnapshot documentSnapshot2= task2.getResult();
                                               if(documentSnapshot2.exists()){
                                                   firebaseFirestore.collection("User").document(Login.getUserID()).collection("greenHouse")
                                                    .document(documentSnapshot.getId()).update("hp", documentSnapshot2.getString("co2_absorption"));
                                               }
                                           }
                                        });
                                    } else {
                                        Log.d(TAG, "no such document");
                                    }
                                } else {
                                    Log.d(TAG, "get failed with", task1.getException());
                                }
                            });
                    }
                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }
            }
        });
    }

    // metodo per calcolare la vita totale delle piante possedute tramite chiamata al database
    public void hpUpdate(View root) {
        FirebaseFirestore.getInstance()
                .collection("User")
                .document(Login.getUserID())
                .collection("greenHouse")
                .get()
                .addOnCompleteListener(task -> {
                   if (task.isSuccessful()) {
                       for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                               totalHp += Double.parseDouble(documentSnapshot.getString("totalHp"));
                       }
                       createRecyclerView(root);
                   }
                });
    }

    // metodo per la creazione della recyclerview
    public void createRecyclerView(View root) {
        scoreView = root.findViewById(R.id.scoreView);
        // aggiornamento la data nel database
        FirestoreDatabase.updateDate();
        // istanziamento della recyclerview
        scoreRecyclerViewAdapter = new ScoreRecyclerViewAdapter(getContext(), totalHp);
        scoreView.setLayoutManager(new LinearLayoutManager(getActivity()));
        scoreView.setAdapter(scoreRecyclerViewAdapter);
    }

    public LocalDate getDate() {
        return LocalDate.now();
    }

    // verifica del cambiamento di giorno
    public boolean needUpdate(LocalDate lastUpdate) {
        if (Period.between(lastUpdate, getDate()).getDays() >= 1)
            return true;
        else
            return false;
    }

}