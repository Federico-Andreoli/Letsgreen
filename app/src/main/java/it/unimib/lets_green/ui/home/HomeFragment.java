package it.unimib.lets_green.ui.home;

import static it.unimib.lets_green.FirestoreDatabase.FirestoreDatabase.TAG;
import static it.unimib.lets_green.Login.getIs_logged;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Text;

import it.unimib.lets_green.DialogFragment;
import it.unimib.lets_green.Login;
import it.unimib.lets_green.R;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private CardView carbonCard;
    private CardView greenHouseCard;
    private TextView scoreTextView;
    private double totalHp;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        scoreTextView = root.findViewById(R.id.score);
        carbonCard = root.findViewById(R.id.cardViewPollution);
        greenHouseCard = root.findViewById(R.id.cardViewPlant);

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

        totalHp = 0.0;

        if(getIs_logged()) {
            FirebaseFirestore.getInstance().collection("User").document(Login.getUserID())
                    .collection("greenHouse").get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    totalHp += Double.parseDouble(document.getData().get("hp").toString());
                                    Log.d(TAG, String.valueOf(totalHp));
                                }
                                scoreTextView.setText("score: " + Double.toString(totalHp));
                            } else
                                Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    });
        }

        return root;
    }
}