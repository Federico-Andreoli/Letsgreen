package it.unimib.lets_green.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;



import java.util.List;

import it.unimib.lets_green.R;
import it.unimib.lets_green.vehicleMakes.VehicleMakes;

public class VehicleMakesAdapter extends RecyclerView.Adapter<it.unimib.lets_green.adapter.VehicleMakesAdapter.MyViewHolder> {

    private Context context;
    private List<VehicleMakes> vehicleMakesList;

    public VehicleMakesAdapter(Context context, List<VehicleMakes> vehicleMakesList) {
        this.context = context;
        this.vehicleMakesList = vehicleMakesList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        v = layoutInflater.inflate(R.layout.row_layout, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final VehicleMakes makes = vehicleMakesList.get(position);
        holder.makesName.setText(vehicleMakesList.get(position).getData().getMakesAttributes().getName().toString());


    }

    @Override
    public int getItemCount() {
        return vehicleMakesList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView makesName;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            makesName = (TextView)itemView.findViewById(R.id.nametxt);
        }
    }
}
