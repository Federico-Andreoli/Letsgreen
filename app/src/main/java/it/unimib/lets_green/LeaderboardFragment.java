package it.unimib.lets_green;

import static it.unimib.lets_green.FirestoreDatabase.FirestoreDatabase.TAG;

import android.os.Bundle;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import it.unimib.lets_green.adapter.UserAdapter1;


public class LeaderboardFragment extends Fragment {

    private RecyclerView recyclerViewLeaderboard;
    private FirebaseFirestore firebaseFirestore;
    private Task<QuerySnapshot> collectionReference;
    private UserAdapter1 userAdapter1;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        TransitionInflater transitionInflater = TransitionInflater.from(getActivity());
        setEnterTransition(transitionInflater.inflateTransition(R.transition.fade));

        View view = inflater.inflate(R.layout.fragment_leaderboard, container, false);

        // impostazione titolo action bar
        ((MainActivity) getActivity()).setActionBarTitle(getString(R.string.leaderboard));

        // impostazione della transizione in entrata per questo fragment
        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                Navigation.findNavController(view).navigate(R.id.navigation_home);
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(getActivity(), callback);

        recyclerViewLeaderboard = view.findViewById(R.id.recyclerViewLeaderboard);

        firebaseFirestore = FirebaseFirestore.getInstance();
//        ottiene tutti i documenti dentro la cartella User, ogni elemento e' salvato in una lista sulla quale verra' costruita la recycler view
        collectionReference = firebaseFirestore.collection("User").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    List<UserFirebase> userFirebaseList = new ArrayList<>();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Log.d(TAG, document.getId());
                        Log.d(TAG, document.get("score").toString());
                        UserFirebase userFirebase = new UserFirebase(document.getId(), document.getString("userName"), Double.parseDouble(document.get("score").toString()));
                        userFirebaseList.add(userFirebase);
                    }
                    Log.d(TAG, userFirebaseList.toString());
                    orderArray(userFirebaseList);
                    setUpRecyclerViewLeaderboard(userFirebaseList);
                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }
            }
        });

        return view;
    }

//    metodo per ordinare gli utenti (della recyclerView) in base al loro score (ordine decrescente)
    private void orderArray(List<UserFirebase> scoreList){

        Collections.sort(scoreList, new Comparator<UserFirebase>() {
            @Override
            public int compare(UserFirebase o1, UserFirebase o2) {
                if (o1.getScore()== o2.getScore())
                    return 0;
                else if (o1.getScore()< o2.getScore())
                    return 1;
                else
                    return -1;
            }
        });
    }

    private void setUpRecyclerViewLeaderboard(List<UserFirebase> list) {
        userAdapter1 = new UserAdapter1(list, getContext());
        recyclerViewLeaderboard.setHasFixedSize(true);
        recyclerViewLeaderboard.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewLeaderboard.setAdapter(userAdapter1);
    }
}