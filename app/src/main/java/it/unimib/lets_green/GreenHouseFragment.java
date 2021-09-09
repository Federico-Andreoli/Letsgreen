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
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
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
    private SwipeRefreshLayout refreshLayout;

    public GreenHouseFragment() {

    }

    public static String getDocumentID() {
        return documentID;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_green_house, container, false);

        // settaggio refresh della pagina
        refreshLayout = view.findViewById(R.id.refresh_layout2);
        refreshLayout.setColorSchemeResources(R.color.green);
        refreshLayout.setOnRefreshListener(() -> {
            // TODO: implementare refresh
            createGreenHouse(view);
            refreshLayout.setRefreshing(false);
        });

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

    // TODO: vedere se il refresh è fattibile
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
