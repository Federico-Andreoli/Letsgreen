package it.unimib.lets_green.ui.path;

import static it.unimib.lets_green.FirestoreDatabase.FirestoreDatabase.TAG;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.unimib.lets_green.FirestoreDatabase.FirestoreDatabase;
import it.unimib.lets_green.MainActivity;
import it.unimib.lets_green.R;
import it.unimib.lets_green.ui.Login.Login;

public class PathFragment extends Fragment  {
    private RecyclerView recyclerViewPath;
    private FloatingActionButton createPath;
    private List<VehiclePath> vehiclePathList;
    private FirebaseFirestore firebaseFirestore;
    private CollectionReference collectionReference;
    private FirestoreRecyclerAdapter adapter;
    private PathAdapterFirestore pathAdapterFirestore;
    private TextView alternativeMessage;
    Double score = 0.0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_path, container, false);
        firebaseFirestore = FirebaseFirestore.getInstance();
        collectionReference = firebaseFirestore.collection("User").document(Login.getUserID()).collection("percorsi");
        vehiclePathList = new ArrayList<VehiclePath>();

        ((MainActivity) getActivity()).setActionBarTitle(getString(R.string.routes));

        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                Navigation.findNavController(view).navigate(R.id.navigation_home);
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(getActivity(), callback);


        recyclerViewPath = view.findViewById(R.id.recyclerViewPath);
        createPath = view.findViewById(R.id.floatingActionButton);

        createPath.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(getView()).navigate(R.id.carbonFragment);
            }
        });
        setUpRecyclerView();
        //riceve il valore passato dal CarbonFragment(l'oggetto Path)
        PathFragmentArgs args = PathFragmentArgs.fromBundle(getArguments());
        VehiclePath vehiclePath = args.getPathObject();

//        se l'oggetto ricevuto e' diverso da null vuol dire che si e' arrivati da CarbonFragment e quindi va aggiornata la recyclerView con
//        l'oggetto appena creato
        if(vehiclePath != null) {
            Map<String, Object> data = new HashMap<>();
            data.put("pathName", vehiclePath.getPathName());
            data.put("pathCarbon", vehiclePath.getPathCarbon());
            firebaseFirestore.collection("User").document(Login.getUserID()).collection("percorsi").add(data);
//          aggiungi l'elemento nel database
//          aggiorna la recyclerview
            setUpRecyclerView();

        }

        return view;
    }

    private void setUpRecyclerView() {
//        RecyclerView costruita con la classe di firestore, fa la query al database e la costruisce con i dati nel database
        Query query = collectionReference;
        FirestoreRecyclerOptions<VehiclePath> options = new FirestoreRecyclerOptions.Builder<VehiclePath>()
                .setQuery(query, VehiclePath.class)
                .build();
        pathAdapterFirestore = new PathAdapterFirestore(options);
        recyclerViewPath.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewPath.setAdapter(pathAdapterFirestore);
        VehiclePath deletedPath = null;
//        metodo per eliminzazione dell'elemento della recycler view tramite lo slide
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

                   int position = viewHolder.getAdapterPosition();
                    VehiclePath tmp = pathAdapterFirestore.getItem(position);
//                    aggiunta di uno Snackbar che annulla la cancellazione del percorso
                    pathAdapterFirestore.deleteItem(viewHolder.getBindingAdapterPosition());
                    Snackbar.make(recyclerViewPath, R.string.deletedRoute, Snackbar.LENGTH_LONG)
                            .setAction("Undo", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Map<String, Object> data = new HashMap<>();
                                    data.put("pathName", tmp.getPathName());
                                    data.put("pathCarbon", tmp.getPathCarbon());
                                    firebaseFirestore.collection("User").document(Login.getUserID()).collection("percorsi").add(data);

                                }
                            }).show();
            }

        }).attachToRecyclerView(recyclerViewPath);

        pathAdapterFirestore.setOnItemClickListener(new PathAdapterFirestore.OnItemClickListener() {
            @Override
            public void inItemClick(DocumentSnapshot documentSnapshot, int position) {
                VehiclePath vehiclePath = documentSnapshot.toObject(VehiclePath.class);
//                id dell'oggetto nel database
                String id = documentSnapshot.getId();
                openDialog(documentSnapshot.getId(), position);

            }
        });

    }
//  metodo per aprire al onclick dell'item un alert dialog
    private void openDialog(String documentSnapshot, int position){
        AlertDialog.Builder addBuilder = new AlertDialog.Builder(getContext());
        addBuilder.setMessage("Do you want to add the Path to your score?")
                .setCancelable(false)
                .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
//                    se cliccato su "yes" si aggiorna lo score dell'utente sottraendo il valore del percorso selezionato, inoltre
//                    si passa il valore del percorso selezionato al GreenHouseFragment che si occupera' di sottrarre alle piante
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseFirestore.getInstance()
                                .collection("User")
                                .document(Login.getUserID())
                                .get()
                                .addOnCompleteListener(task ->  {
                            if(task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                if(document.exists()) {

                                    score = Double.parseDouble(document.get("score").toString());
                                    score -= Double.parseDouble(pathAdapterFirestore.getItem(position).getPathCarbon());
                                    FirestoreDatabase.updateScore(score);
                                    PathFragmentDirections.ActionPathFragmentToGreenHouseFragment action = PathFragmentDirections.actionPathFragmentToGreenHouseFragment();
                                    action.setScoreHp(Float.parseFloat(pathAdapterFirestore.getItem(position).getPathCarbon()));
                                    Log.d(TAG,pathAdapterFirestore.getItem(position).getPathCarbon());
                                    Navigation.findNavController(getView()).navigate(action);
                                }
                            } else {
                                Log.d(TAG, "Error getting documents: ", task.getException());
                            }
                    });
                        Toast.makeText(getParentFragment().getContext(),
                                "The route " + pathAdapterFirestore.getItem(position).getPathName() + " has been added to the score",
                                Toast.LENGTH_LONG).show();
                                }
                })
                .setNegativeButton("no", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .setTitle("Confirm");
        addBuilder.show();
    }

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