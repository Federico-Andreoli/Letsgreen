package it.unimib.lets_green.ui.home;

import static it.unimib.lets_green.FirestoreDatabase.FirestoreDatabase.TAG;
import static it.unimib.lets_green.Login.getIs_logged;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Text;

import java.time.LocalDate;
import java.time.Period;
import java.util.HashMap;
import java.util.Map;

import it.unimib.lets_green.DialogFragment;
import it.unimib.lets_green.Login;
import it.unimib.lets_green.R;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private CardView carbonCard;
    private CardView greenHouseCard;
    private double totalHp = 0.0;
    private double totalCo2;
    private LocalDate lastUpdate;
    private RecyclerView scoreView;
    private ScoreRecyclerViewAdapter scoreRecyclerViewAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
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
                    if(task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if(document.exists()) {
                            lastUpdate = LocalDate.parse(document.get("lastUpdate").toString());
                        } else {
                            Log.d(TAG, "no such document");
                        }
                    } else {
                        Log.d(TAG, "get failed with", task.getException());
                    }
                    Log.d(TAG, "last update: " + lastUpdate.toString());
                    // creazione recycler view e aggiornamento hp se necessario
                    if(needUpdate(lastUpdate)) {
                        hpUpdate(root);
                        co2EmissionsUpdate(root);
                    }
                });
        }

        return root;
    }

    public void co2EmissionsUpdate(View root)  {
        FirebaseFirestore.getInstance()
            .collection("User")
            .document(Login.getUserID())
            .collection("percorsi")
            .get()
            .addOnCompleteListener(task -> {
                if(task.isSuccessful()) {
                    for(QueryDocumentSnapshot document : task.getResult()) {
                        totalCo2 += Double.parseDouble((document.getData()).get("pathCarbon").toString());
                    }
                }
                else
                    Log.d(TAG, "Error getting documents: ", task.getException());
                createRecyclerView(root);
            });
    }

    public void hpUpdate(View root) {
        FirebaseFirestore.getInstance()
            .collection("User")
            .document(Login.getUserID())
            .collection("greenHouse")
            .get()
            .addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        totalHp += Double.parseDouble(document.getData().get("hp").toString());
                    }
                } else
                    Log.d(TAG, "Error getting documents: ", task.getException());
                FirebaseFirestore.getInstance().collection("User")
                        .document(Login.getUserID())
                        .update("totalHp", totalHp);
                FirebaseFirestore.getInstance().collection("User")
                        .document(Login.getUserID())
                        .update("lastUpdate", LocalDate.now().minusDays(1).toString());
                createRecyclerView(root);
            });
    }

    public void createRecyclerView(View root) {
        scoreView = root.findViewById(R.id.scoreView);
        scoreRecyclerViewAdapter = new ScoreRecyclerViewAdapter(getContext(), totalHp, totalCo2);
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