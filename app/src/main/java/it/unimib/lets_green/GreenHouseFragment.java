package it.unimib.lets_green;

import android.os.Bundle;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;

import it.unimib.lets_green.ui.Login.Login;


public class GreenHouseFragment extends Fragment {
    private FirebaseFirestore firebaseFirestore;
    private RecyclerView mRecyclerView;
    private GreenHouseAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private static String documentID;
    private ArrayList<GreenHouseItem> plantList = new ArrayList<>();
//    private SwipeRefreshLayout refreshLayout;

    public GreenHouseFragment() {

    }

    public static String getDocumentID() {
        return documentID;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_green_house, container, false);

        ((MainActivity) requireActivity()).setActionBarTitle(getString(R.string.greenHouse));

        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {// gestisce il pulsante di back
            @Override
            public void handleOnBackPressed() {
                Navigation.findNavController(view).navigate(R.id.navigation_home);
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(getActivity(), callback);

        createGreenHouse(view);

        return view;
    }


    private class plantViewHolder extends RecyclerView.ViewHolder{

        private TextView namePlant;
        public plantViewHolder(@NonNull View itemView) {
            super(itemView);
            namePlant=itemView.findViewById(R.id.namePlant);
        }
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
        firebaseFirestore = FirebaseFirestore.getInstance();
        Query query = firebaseFirestore.collection("User").document(Login.getUserID()).collection("greenHouse");
        FirestoreRecyclerOptions<GreenHouseItem> options= new FirestoreRecyclerOptions.Builder<GreenHouseItem>().setQuery(query, GreenHouseItem.class).build();

        mRecyclerView = view.findViewById(R.id.RecyclerView);
        mRecyclerView.setHasFixedSize(true);
        mAdapter = new GreenHouseAdapter(options,plantList);

        mAdapter.setOnItemClickListener(new GreenHouseAdapter.onItemClickListener(){
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                GreenHouseItem itemPlant=documentSnapshot.toObject(GreenHouseItem.class);
                String documentID=documentSnapshot.getId();
            }
        });

        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        mRecyclerView.setAdapter(mAdapter);
    }
}
