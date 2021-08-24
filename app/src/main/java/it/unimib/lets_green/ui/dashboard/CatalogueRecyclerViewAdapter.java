package it.unimib.lets_green.ui.dashboard;

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

    private List<String> stringList;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(String s);
    }

    public CatalogueRecyclerViewAdapter(List<String> stringList, OnItemClickListener onItemClickListener) {
        this.stringList = stringList;
        listener = onItemClickListener;
    }

    @NonNull
    @NotNull
    @Override
    public CatalogViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.catalogue_item, parent, false);

        return new CatalogViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull CatalogViewHolder holder, int position) {
        holder.bind(stringList.get(position));
    }

    @Override
    public int getItemCount() {
        return stringList.size();
    }

    public class CatalogViewHolder extends RecyclerView.ViewHolder {

        private TextView nameTextView;
        private ImageView imageView;

        public CatalogViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.name);
            imageView = itemView.findViewById(R.id.image);
        }

        public void bind(String s) {
            nameTextView.setText(s);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(s);
                }
            });
        }
    }
}