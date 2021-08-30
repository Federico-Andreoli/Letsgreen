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
import it.unimib.lets_green.VehiclePath;

public class PathAdapter extends RecyclerView.Adapter<PathAdapter.My2ViewHolder> {

    private Context context;
    private List<VehiclePath> pathVehicleList;
    private LayoutInflater mInflater;

    public PathAdapter(Context context, List<VehiclePath> pathVehicleList) {
        this.context = context;
        this.pathVehicleList = pathVehicleList;
        this.mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public My2ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = mInflater.from(context).inflate(R.layout.row_path, null);
        return new PathAdapter.My2ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull My2ViewHolder holder, int position) {
        holder.namePath.setText(pathVehicleList.get(position).getPathName());
        holder.carbonPath.setText(pathVehicleList.get(position).getPathCarbon());
    }

    @Override
    public int getItemCount() {
        return pathVehicleList.size();
    }

    public class My2ViewHolder extends RecyclerView.ViewHolder {
        TextView namePath;
        TextView carbonPath;

        public My2ViewHolder(@NonNull View itemView) {
            super(itemView);
            namePath = (TextView)itemView.findViewById(R.id.namePath);
            carbonPath =(TextView)itemView.findViewById(R.id.carbonNumber);
        }
    }
}
