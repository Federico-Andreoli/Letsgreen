package it.unimib.lets_green;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class GreenHouseAdapter extends FirestoreRecyclerAdapter<GreenHouseItem, GreenHouseAdapter.GreenHouseAdapterHolder> {

    private ArrayList<GreenHouseItem> mPlantList;
    private GreenHouseAdapter.onItemClickListener mListener;
    private FirebaseFirestore firestore=FirebaseFirestore.getInstance();
    private StorageReference gsReference;

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
        holder.mImageView.setVisibility(View.GONE);
        // scaricamento e settaggio immagine
        gsReference = FirebaseStorage.getInstance().getReferenceFromUrl("gs://lets-green-b9ddf.appspot.com/" + model.getNamePlant() + ".png");
        final long ONE_MEGABYTE = 1024 * 1024; // dimensione massima dell'immagine da scaricare
        gsReference.getBytes(ONE_MEGABYTE).addOnSuccessListener(bytes -> {
            Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            holder.mImageView.setImageBitmap(bmp);
            holder.mProgressBar.setVisibility(View.GONE);
            holder.mImageView.setVisibility(View.VISIBLE);
        }).addOnFailureListener(exception -> {
            // TODO: Handle any errors
        });
        holder.mNamePlant.setText(model.getNamePlant().substring(0, 1).toUpperCase() + model.getNamePlant().substring(1).toLowerCase());
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

        private ImageView mImageView;
        private TextView mNamePlant;
        private ImageView mDelete;
        private ProgressBar mProgressBar;

        public GreenHouseAdapterHolder(@NonNull View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.imageView2);
            mNamePlant = itemView.findViewById(R.id.namePlant);
            mDelete = itemView.findViewById(R.id.deletePlant);
            mProgressBar = itemView.findViewById(R.id.progressBar2);


            mDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        int position = getBindingAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mListener.onItemClick(getSnapshots().getSnapshot(position), position);
                        }
                    }
                }
            });
        }
    }
}