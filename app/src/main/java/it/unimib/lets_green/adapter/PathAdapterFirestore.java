package it.unimib.lets_green.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import it.unimib.lets_green.R;
import it.unimib.lets_green.VehiclePath;

public class PathAdapterFirestore extends FirestoreRecyclerAdapter<VehiclePath, PathAdapterFirestore.PathHolder> {


    public PathAdapterFirestore(@NonNull FirestoreRecyclerOptions<VehiclePath> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull PathHolder holder, int position, @NonNull VehiclePath model) {
        holder.namePath.setText(model.getPathName());
        holder.carbonPath.setText(model.getPathCarbon());
    }

    @NonNull
    @Override
    public PathHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_path, parent, false);
        return new PathHolder(v);
    }

    class PathHolder extends RecyclerView.ViewHolder {
        TextView namePath;
        TextView carbonPath;

        public PathHolder(@NonNull View itemView) {
            super(itemView);
            namePath = (TextView)itemView.findViewById(R.id.namePath);
            carbonPath =(TextView)itemView.findViewById(R.id.carbonNumber);
        }
    }
}
