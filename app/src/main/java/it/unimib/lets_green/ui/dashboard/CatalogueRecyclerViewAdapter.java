package it.unimib.lets_green.ui.dashboard;

import static it.unimib.lets_green.FirestoreDatabase.FirestoreDatabase.TAG;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import org.jetbrains.annotations.NotNull;
import java.util.List;
import it.unimib.lets_green.R;

public class CatalogueRecyclerViewAdapter extends RecyclerView.Adapter<CatalogueRecyclerViewAdapter.CatalogViewHolder> {

    private List<Plant> plants;
    private OnItemClickListener listener;
    private Context context;

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

        holder.nameTextView.setText(plants.get(position).getName());
        holder.imageView.setImageResource(R.drawable.ic_app_background);

    }

    @Override
    public int getItemCount() {
        return plants.size();
    }

    public class CatalogViewHolder extends RecyclerView.ViewHolder {

        TextView nameTextView;
        ImageView imageView;

        public CatalogViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            nameTextView = (TextView) itemView.findViewById(R.id.itemName);
            imageView = (ImageView) itemView.findViewById(R.id.imageView2);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(getAdapterPosition());
                }
            });
        }

        /*
        public void bind(final ContentItem item, final OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(position);
                }
            });
        }
        */
    }
}