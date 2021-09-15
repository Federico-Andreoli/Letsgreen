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
import it.unimib.lets_green.vehicleModel.VehicleModels;

public class VehicleModelsAdapter extends RecyclerView.Adapter<VehicleModelsAdapter.ViewHolder> {

    private Context context;
    private List<VehicleModels> vehicleModelsList;
    private LayoutInflater mInflater;
    final VehicleModelsAdapter.OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(VehicleModels item);
    }

    public VehicleModelsAdapter(List<VehicleModels> list, Context context, VehicleModelsAdapter.OnItemClickListener listener){
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.vehicleModelsList = list;
        this.mListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = mInflater.inflate(R.layout.row_model, parent, false);
        return new VehicleModelsAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.nameModel.setText(vehicleModelsList.get(holder.getBindingAdapterPosition()).getData().getAttributes().getName());
        holder.yearModel.setText(vehicleModelsList.get(holder.getBindingAdapterPosition()).getData().getAttributes().getYear().toString());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mListener.onItemClick(vehicleModelsList.get(holder.getBindingAdapterPosition()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return vehicleModelsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameModel;
        TextView yearModel;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameModel = itemView.findViewById(R.id.models);
            yearModel = itemView.findViewById(R.id.modelYear);
        }
    }

}
