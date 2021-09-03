package it.unimib.lets_green;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;


public class GreenHouseFragment extends Fragment {
    private FirebaseFirestore firebaseFirestore;
    private RecyclerView mRecyclerView;
    private GreenHouseAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private static String documentID;

    private ArrayList<GreenHouseItem> plantList = new ArrayList<>();


    public GreenHouseFragment() {

    }

    public static String getDocumentID() {
        return documentID;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_green_house, container, false);

        firebaseFirestore = FirebaseFirestore.getInstance();


        mRecyclerView = view.findViewById(R.id.RecyclerView);
        mRecyclerView.setHasFixedSize(true);





        Query query=firebaseFirestore.collection("User").document("DYkyohMNuAPE4BC94bsTsmP5O9Q2").collection("greenHouse");
        FirestoreRecyclerOptions<GreenHouseItem> options= new FirestoreRecyclerOptions.Builder<GreenHouseItem>().setQuery(query, GreenHouseItem.class).build();

        mAdapter= new GreenHouseAdapter(options,plantList);

        mAdapter.setOnItemClickListener(new GreenHouseAdapter.onItemClickListener(){
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                GreenHouseItem itemPlant=documentSnapshot.toObject(GreenHouseItem.class);
                String documentID=documentSnapshot.getId();
            }

        });

        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));

        mRecyclerView.setAdapter(mAdapter);



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
}
