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
    private OnItemClickListener mListener;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
       mListener = listener;
    }


    public VehicleMakesAdapter(Context context, List<VehicleMakes> vehicleMakesList) {
        this.context = context;
        this.vehicleMakesList = vehicleMakesList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        v = layoutInflater.inflate(R.layout.row_example, parent, false);
        return new MyViewHolder(v, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final VehicleMakes makes = vehicleMakesList.get(position);
        holder.makesName.setText(vehicleMakesList.get(position).getData().getMakesAttributes().getName().toString());
        holder.makesNumber.setText(vehicleMakesList.get(position).getData().getMakesAttributes().getNumberOfModels().toString());

    }

    @Override
    public int getItemCount() {
        return vehicleMakesList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView makesNumber;
        TextView makesName;

        public MyViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);

            makesName = (TextView)itemView.findViewById(R.id.modelName);
            makesNumber =(TextView)itemView.findViewById(R.id.modelNumbers);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listener.onItemClick(position);
                        }
                    }
                }
            });

        }
    }
}
