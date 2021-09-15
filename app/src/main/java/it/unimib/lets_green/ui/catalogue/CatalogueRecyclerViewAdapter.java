package it.unimib.lets_green.ui.catalogue;


import static it.unimib.lets_green.FirestoreDatabase.FirestoreDatabase.TAG;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import org.jetbrains.annotations.NotNull;
import java.util.List;
import it.unimib.lets_green.R;

public class CatalogueRecyclerViewAdapter extends RecyclerView.Adapter<CatalogueRecyclerViewAdapter.CatalogViewHolder> {

    private List<Plant> plants;
    private OnItemClickListener listener;
    private Context context;
    private StorageReference gsReference;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public CatalogueRecyclerViewAdapter(Context context, List<Plant> plants, OnItemClickListener listener) {
        this.context = context;
        this.plants = plants;
        this.listener = listener;
    }

    @NonNull
    @NotNull
    @Override
    public CatalogViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.catalogue_item, parent, false);
        return new CatalogViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull CatalogViewHolder holder, int position) {
        holder.imageView.setVisibility(View.GONE);
        holder.nameTextView.setText(plants.get(position).getName().substring(0, 1).toUpperCase() + plants.get(position).getName().substring(1).toLowerCase());

        // scaricamento e settaggio immagine
        gsReference = FirebaseStorage.getInstance().getReferenceFromUrl("gs://lets-green-b9ddf.appspot.com/" + plants.get(position).getName() + ".png");
        final long ONE_MEGABYTE = 1024 * 1024; // dimensione massima dell'immagine da scaricare
        gsReference.getBytes(ONE_MEGABYTE).addOnSuccessListener(bytes -> {
            Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            holder.imageView.setImageBitmap(bmp);
            holder.progressBar.setVisibility(View.GONE);
            holder.imageView.setVisibility(View.VISIBLE);
        }).addOnFailureListener(exception -> {
            Log.d(TAG, "image failure");
        });
    }

    @Override
    public int getItemCount() {
        return plants.size();
    }

    public class CatalogViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        ImageView imageView;
        ProgressBar progressBar;

        public CatalogViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.itemName);
            imageView = itemView.findViewById(R.id.imageView);
            progressBar = itemView.findViewById(R.id.progressBar2);
            itemView.setOnClickListener(v -> listener.onItemClick(getBindingAdapterPosition()));
        }
    }
}