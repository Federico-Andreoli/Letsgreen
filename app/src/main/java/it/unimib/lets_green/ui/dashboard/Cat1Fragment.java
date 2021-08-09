package it.unimib.lets_green.ui.dashboard;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import it.unimib.lets_green.R;

public class Cat1Fragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cat1, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        List<String> stringList = new ArrayList<String>();

        for(int i = 0; i < 20; i++) {
            stringList.add("elemento " + i);
        }

        RecyclerView recyclerView = view.findViewById(R.id.cat1_view);
        CatalogueRecyclerViewAdapter catalogRecyclerViewAdapter = new CatalogueRecyclerViewAdapter(stringList, new CatalogueRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(String s) {
                Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
            }
        });
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerView.setAdapter(catalogRecyclerViewAdapter);
    }
}