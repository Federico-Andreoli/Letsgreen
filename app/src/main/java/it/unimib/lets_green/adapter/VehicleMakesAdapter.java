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
import it.unimib.lets_green.vehicleModel.VehicleModels;

public class VehicleMakesAdapter extends RecyclerView.Adapter<VehicleMakesAdapter.MyViewHolder> {

    private Context context;
    private List<VehicleMakes> vehicleMakesList;
    private LayoutInflater mInflater;
    final VehicleMakesAdapter.OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(VehicleMakes item);
    }

    public VehicleMakesAdapter(List<VehicleMakes> list, Context context, VehicleMakesAdapter.OnItemClickListener listener){
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.vehicleMakesList = list;
        this.mListener = listener;
    }

//    public interface OnItemClickListener{
//        void onItemClick(int position);
//    }
//
//    public void setOnItemClickListener(OnItemClickListener listener) {
//       mListener = listener;
//    }
//
//
//    public VehicleMakesAdapter(Context context, List<VehicleMakes> vehicleMakesList) {
//        this.context = context;
//        this.vehicleMakesList = vehicleMakesList;
//    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View v;
//        LayoutInflater layoutInflater = LayoutInflater.from(context);
//        v = layoutInflater.inflate(R.layout.row_example, parent, false);
//        return new MyViewHolder(v, mListener);
        View v = mInflater.from(context).inflate(R.layout.row_example, null);
        return new VehicleMakesAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final VehicleMakes makes = vehicleMakesList.get(position);
        holder.makesName.setText(vehicleMakesList.get(position).getData().getMakesAttributes().getName().toString());
        holder.makesNumber.setText(vehicleMakesList.get(position).getData().getMakesAttributes().getNumberOfModels().toString());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onItemClick(vehicleMakesList.get(position));
            }
        });

    }

    @Override
    public int getItemCount() {
        return vehicleMakesList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView makesNumber;
        TextView makesName;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            makesName = (TextView)itemView.findViewById(R.id.modelName);
            makesNumber =(TextView)itemView.findViewById(R.id.modelNumbers);
//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if(listener != null){
//                        int position = getAdapterPosition();
//                        if(position != RecyclerView.NO_POSITION){
//                            listener.onItemClick(position);
//                        }
//                    }
//                }
//            });

        }
    }
}