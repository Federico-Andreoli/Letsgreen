package it.unimib.lets_green;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import it.unimib.lets_green.adapter.PathAdapter;

import static it.unimib.lets_green.FirestoreDatabase.FirestoreDatabase.TAG;


public class PathFragment extends Fragment {
    private RecyclerView recyclerViewPath;
    private FloatingActionButton createPath;
    private List<VehiclePath> vehiclePathList;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_path, container, false);
        
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
        PathFragmentArgs args = PathFragmentArgs.fromBundle(getArguments());
        VehiclePath vehiclePath = args.getPathObject();
        if(vehiclePath != null) {
            Log.d(TAG, vehiclePath.toString());
            Log.d(TAG, "werfsdrfdfgr");

            vehiclePathList.add(vehiclePath);
            PutDataIntoRecyclerView(vehiclePathList);
        }
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
    private void PutDataIntoRecyclerView(List<VehiclePath> listPath) {
        PathAdapter pathAdapter = new PathAdapter(getContext(), listPath);
        recyclerViewPath.setHasFixedSize(true);
        recyclerViewPath.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewPath.setAdapter(pathAdapter);
    }
}