package it.unimib.lets_green;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class GreenHouseFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private GreenHouseAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private Button buttonRemove;
    private ArrayList<GreenHouseItem> plantList = new ArrayList<>();


    public GreenHouseFragment() {

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_green_house, container, false);

        buttonRemove = (Button)view.findViewById(R.id.button_remove);
        mRecyclerView = view.findViewById(R.id.RecyclerView);
        mRecyclerView.setHasFixedSize(true);
        mAdapter = new GreenHouseAdapter(plantList);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        mRecyclerView.setAdapter(mAdapter);


        plantList.add(new GreenHouseItem(R.drawable.fiore, "Pianta 1"));
        plantList.add(new GreenHouseItem(R.drawable.fiore, "Pianta 2"));
        plantList.add(new GreenHouseItem(R.drawable.fiore, "Pianta 3"));



        mAdapter.setOnItemClickListener(new GreenHouseAdapter.onItemClickListener() {
            @Override
            public void onItemClick(int position) {
                plantList.get(position);
            }

            @Override
            public void onDeleteClick(int position) {
                removeItem(position);
            }
        });
        return view;
    }
    public void removeItem(int position) {
        plantList.remove(position);
        mAdapter.notifyItemRemoved(position);
    }
}