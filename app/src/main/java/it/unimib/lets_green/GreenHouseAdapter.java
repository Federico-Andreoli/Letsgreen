package it.unimib.lets_green;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;

public class GreenHouseAdapter extends RecyclerView.Adapter<GreenHouseAdapter.GreenHouseViewHolder> {
    private ArrayList<GreenHouseItem> mPlantList;
    private onItemClickListener mListener;


    public interface onItemClickListener {
       
        void onDeleteClick(int position);

        void onItemClick(DocumentSnapshot documentSnapshot,int position);
    }

    public void setOnItemClickListener(onItemClickListener listener) {
        mListener = listener;
    }

    public static class GreenHouseViewHolder extends RecyclerView.ViewHolder {
        public ImageView mImageView;
        public TextView mNamePlant;
        public ImageView mDelete;

        public GreenHouseViewHolder(View itemView, onItemClickListener listener) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.image);
            mNamePlant = itemView.findViewById(R.id.namePlant);
            mDelete = itemView.findViewById(R.id.deletePlant);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getBindingAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
//                            listener.onItemClick(getSnapshots().getSnapshot(position), position);
                        }


                    }
                }
            });
            mDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onDeleteClick(position);
                        }
                    }
                }
            });

        }
    }
    public GreenHouseAdapter(ArrayList < GreenHouseItem > plantList) {
                mPlantList = plantList;
            }
            @Override
            public GreenHouseViewHolder onCreateViewHolder (ViewGroup parent,int viewType){
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.plant_item, parent, false);
                GreenHouseViewHolder evh = new GreenHouseViewHolder(v, mListener);
                return evh;
            }
            @Override
            public void onBindViewHolder (GreenHouseViewHolder holder,int position){
                GreenHouseItem currentItem = mPlantList.get(position);
                holder.mImageView.setImageResource(currentItem.getImageResource());
                holder.mNamePlant.setText(currentItem.getNamePlant());
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

//                        mListener.onItemClick(mPlantList.get(position));
                    }
                });
            }
            @Override
            public int getItemCount () {

                return mPlantList.size();
            }
    public void deleteItem(int position) {
//        getSnapshots().getSnapshot(position).getReference().delete();
    }

}

