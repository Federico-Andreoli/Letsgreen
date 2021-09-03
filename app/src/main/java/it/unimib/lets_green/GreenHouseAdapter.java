package it.unimib.lets_green;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class GreenHouseAdapter extends FirestoreRecyclerAdapter<GreenHouseItem, GreenHouseAdapter.GreenHouseAdapterHolder> {
    private ArrayList<GreenHouseItem> mPlantList;
    private GreenHouseAdapter.onItemClickListener mListener;
    private FirebaseFirestore firestore=FirebaseFirestore.getInstance();



    public void setOnItemClickListener(GreenHouseAdapter.onItemClickListener Listener) {
        mListener = Listener;
    }

    public interface onItemClickListener {
        void onItemClick(DocumentSnapshot documentSnapshot, int position);
    }


    public GreenHouseAdapter(@NonNull FirestoreRecyclerOptions<GreenHouseItem> options, ArrayList<GreenHouseItem> plantList) {
        super(options);
        mPlantList = plantList;
    }

    @Override
    protected void onBindViewHolder(@NonNull GreenHouseAdapterHolder holder, int position, @NonNull GreenHouseItem model) {
        holder.mImageView.setImageResource(model.getImageResource());
        holder.mNamePlant.setText(model.getNamePlant());
        holder.mDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSnapshots().getSnapshot(holder.getAbsoluteAdapterPosition()).getReference().delete();

            }
        });
    }

    @NonNull
    @Override
    public GreenHouseAdapterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.plant_item,
                parent, false);
        return new GreenHouseAdapterHolder(v);
    }



    class GreenHouseAdapterHolder extends RecyclerView.ViewHolder {

        public ImageView mImageView;
        public TextView mNamePlant;
        public ImageView mDelete;

        public GreenHouseAdapterHolder(@NonNull View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.image);
            mNamePlant = itemView.findViewById(R.id.namePlant);
            mDelete = itemView.findViewById(R.id.deletePlant);

            mDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mListener.onItemClick(getSnapshots().getSnapshot(position), position);
                        }
                    }
                }
            });

        }
    }
}


