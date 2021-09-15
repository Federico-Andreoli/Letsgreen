package it.unimib.lets_green;

import static it.unimib.lets_green.FirestoreDatabase.FirestoreDatabase.TAG;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

import it.unimib.lets_green.FirestoreDatabase.FirestoreDatabase;

public class GreenHouseAdapter extends FirestoreRecyclerAdapter<GreenHouseItem, GreenHouseAdapter.GreenHouseAdapterHolder> {

    private ArrayList<GreenHouseItem> mPlantList;
    private GreenHouseAdapter.onItemClickListener mListener;
    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();
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
        Log.d(TAG, mPlantList.toString());
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

        holder.setPlantName(model.getNamePlant());
        holder.mNamePlant.setText(model.getNamePlant().substring(0, 1).toUpperCase() + model.getNamePlant().substring(1).toLowerCase());
        holder.hp.setText(model.getHp().toString());
        holder.mDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onItemClick(getSnapshots().getSnapshot(holder.getBindingAdapterPosition()), holder.getBindingAdapterPosition());
                FirestoreDatabase.subtractPlantFromScore(Double.parseDouble(getSnapshots().getSnapshot(holder.getBindingAdapterPosition()).get("hp").toString()));
                getSnapshots().getSnapshot(holder.getBindingAdapterPosition()).getReference().delete();
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
        private Button mButton;
        private String plantName;
        private TextView hp;

        public void setPlantName(String plantName) {
            this.plantName = plantName;
        }

        public GreenHouseAdapterHolder(@NonNull View itemView) {
            super(itemView);
            hp = itemView.findViewById(R.id.hpPlant);
            mImageView = itemView.findViewById(R.id.imageView2);
            mNamePlant = itemView.findViewById(R.id.namePlant);
            mDelete = itemView.findViewById(R.id.deletePlant);
            mProgressBar = itemView.findViewById(R.id.progressBar2);
            mButton = itemView.findViewById(R.id.viewButton);

            mButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    FirebaseFirestore.getInstance().collection("plants").document(plantName).get().addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                bundle.putString("name", document.getId());
                                bundle.putString("common_name", document.getData().get("common_name").toString());
                                bundle.putString("species", document.getData().get("species").toString());
                                bundle.putString("description", document.getData().get("description").toString());
                                bundle.putString("co2_absorption", document.getData().get("co2_absorption").toString());
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                        Navigation.findNavController(v).navigate(R.id.plantFragment, bundle);
                    });
                }
            });

            mDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        int position = getBindingAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {

                        }
                    }
                }
            });
        }
    }
}