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
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;


public class GreenHouseFragment extends Fragment {
    private FirebaseFirestore firebaseFirestore;
    private RecyclerView mRecyclerView;
    private GreenHouseAdapter2 mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private ArrayList<GreenHouseItem> plantList = new ArrayList<>();


    public GreenHouseFragment() {

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_green_house, container, false);

        firebaseFirestore = FirebaseFirestore.getInstance();


        mRecyclerView = view.findViewById(R.id.RecyclerView);
        mRecyclerView.setHasFixedSize(true);



//        plantList.add(new GreenHouseItem(R.drawable.fiore, "Pianta 1"));
//        plantList.add(new GreenHouseItem(R.drawable.fiore, "Pianta 2"));
//        plantList.add(new GreenHouseItem(R.drawable.fiore, "Pianta 3"));




//        mAdapter.setOnItemClickListener(new GreenHouseAdapter.onItemClickListener() {
//            @Override
//            public void onItemClick(int position) {
//                plantList.get(position);
//            }
//
//            @Override
//            public void onDeleteClick(int position) {
//                removeItem(position);
//            }
//        });



        Query query=firebaseFirestore.collection("User").document("DYkyohMNuAPE4BC94bsTsmP5O9Q2").collection("greenHouse");
        FirestoreRecyclerOptions<GreenHouseItem> options= new FirestoreRecyclerOptions.Builder<GreenHouseItem>().setQuery(query, GreenHouseItem.class).build();
        mAdapter= new GreenHouseAdapter2(options,plantList);
        mAdapter.setOnItemClickListener(new GreenHouseAdapter2.onItemClickListener(){
                                            @Override
                                            public void onDeleteClick(int position) {
                                               plantList.remove(position);
                                               mAdapter.notifyItemRemoved(position);
                                            }
                                        });
//        adapter= new FirestoreRecyclerAdapter<GreenHouseItem, plantViewHolder>(options) {
//            @NonNull
//            @Override
//            public plantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//                View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.plant_item,parent,false);
//
//                return new plantViewHolder(view);
//            }
//
//            @Override
//            protected void onBindViewHolder(@NonNull plantViewHolder holder, int position, @NonNull GreenHouseItem model) {
//            holder.namePlant.setText(model.getNamePlant());
//            }
//        };
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
//        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setAdapter(mAdapter);
        return view;
    }

//    public void removeItem(int position) {
//        plantList.remove(position);
//        mAdapter.notifyItemRemoved(position);
//    }

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