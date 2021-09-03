package it.unimib.lets_green;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.unimib.lets_green.adapter.PathAdapterFirestore;


public class PathFragment extends Fragment {
    private RecyclerView recyclerViewPath;
    private FloatingActionButton createPath;
    private List<VehiclePath> vehiclePathList;
    private FirebaseFirestore firebaseFirestore;
    private CollectionReference collectionReference;
    private FirestoreRecyclerAdapter adapter;
    private PathAdapterFirestore pathAdapterFirestore;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_path, container, false);
        firebaseFirestore = FirebaseFirestore.getInstance();
        collectionReference = firebaseFirestore.collection("User").document(Login.getUserID()).collection("percorsi");
        vehiclePathList = new ArrayList<VehiclePath>();

//                ModelFragmentArgs.fromBundle(getArguments()).getIdMakes();
        recyclerViewPath = view.findViewById(R.id.recyclerViewPath);
        createPath = view.findViewById(R.id.floatingActionButton);


        createPath.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(getView()).navigate(R.id.carbonFragment);
            }
        });
        setUpRecyclerView();
        PathFragmentArgs args = PathFragmentArgs.fromBundle(getArguments());
        VehiclePath vehiclePath = args.getPathObject();
        if(vehiclePath != null) {
            Map<String, Object> data = new HashMap<>();
            data.put("pathName", vehiclePath.getPathName());
            data.put("pathCarbon", vehiclePath.getPathCarbon());
            firebaseFirestore.collection("User").document(Login.getUserID()).collection("percorsi").add(data);
//          aggiungi l'elemento nel database

            setUpRecyclerView();

            //aggiungo nella lista l'elemento creato
            //pusho
            // metto nella recycler


//            vehiclePathList.add(vehiclePath);
//            PutDataIntoRecyclerView(vehiclePathList);
        }

        //fare la query e mettere nella lista

//        adapter = new FirestoreRecyclerAdapter<VehiclePath, PathViewHolder>(options) {
//            @NonNull
//            @Override
//            public PathViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_path, parent, false);
//                return new PathViewHolder(v);
//            }
//
//            @Override
//            protected void onBindViewHolder(@NonNull PathViewHolder holder, int position, @NonNull VehiclePath model) {
//                holder.namePath.setText(model.getPathName());
//                holder.carbonPath.setText(model.getPathCarbon());
//            }
//        };


        return view;
    }

//    @Override
//    public void onResume() {
//        super.onResume();
//
////        String pathInfo = CarbonFragmentArgs.fromBundle(getArguments()).getIdModel();
////        CarbonFragmentArgs cardPath = CarbonFragmentArgs.fromBundle(getArguments());
////        vehiclePathList.add(cardPath);
////        PutDataIntoRecyclerView((List<VehiclePath>) cardPath);
//
//    }
    private void setUpRecyclerView() {
        Query query = collectionReference;
        FirestoreRecyclerOptions<VehiclePath> options = new FirestoreRecyclerOptions.Builder<VehiclePath>()
                .setQuery(query, VehiclePath.class)
                .build();
        pathAdapterFirestore = new PathAdapterFirestore(options);
        recyclerViewPath.setHasFixedSize(true);
        recyclerViewPath.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewPath.setAdapter(pathAdapterFirestore);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
//                switch (direction){
//                    case ItemTouchHelper.LEFT:
//                        break;
//                }
//                ItemTouchHelper.LEFT
                    pathAdapterFirestore.deleteItem(viewHolder.getAdapterPosition());

            }

//            AGGIUNGERE LO SWIPE COLORATO ED UNDO creaore pure l'onclik con pop up di aggiunta allo score
        }).attachToRecyclerView(recyclerViewPath);

        pathAdapterFirestore.setOnItemClickListener(new PathAdapterFirestore.OnItemClickListener() {
            @Override
            public void inItemClick(DocumentSnapshot documentSnapshot, int position) {
                VehiclePath vehiclePath = documentSnapshot.toObject(VehiclePath.class);
//                id dell'oggetto nel database!!!!
                String id = documentSnapshot.getId();
                Toast.makeText(getContext(), "Position: " + position + "ID: " + id, Toast.LENGTH_SHORT).show();
//                qua metto codice per startare un nuovo fragment e passare i vari dati!
//                se voglio passare l'intero oggetto String path = documentSnapshot.getReference().getPath()
            }
        });

////        List<VehiclePath> listPath
////        PathAdapter pathAdapter = new PathAdapter(getContext(), listPath);
//        recyclerViewPath.setHasFixedSize(true);
//        recyclerViewPath.setLayoutManager(new LinearLayoutManager(getContext()));
////        recyclerViewPath.setAdapter(pathAdapter);
//        recyclerViewPath.setAdapter(adapter);
    }

//    private class PathViewHolder extends RecyclerView.ViewHolder {
//
//        TextView namePath;
//        TextView carbonPath;
//
//        public PathViewHolder(@NonNull View itemView) {
//            super(itemView);
//            namePath = (TextView)itemView.findViewById(R.id.namePath);
//            carbonPath =(TextView)itemView.findViewById(R.id.carbonNumber);
//        }
//    }
    @Override
    public void onStart() {
        super.onStart();
        pathAdapterFirestore.startListening();
    }
//    l'app in background non fara alcuna richiesta e non spreca risorse
    @Override
    public void onStop() {
        super.onStop();
        pathAdapterFirestore.stopListening();
    }



}
