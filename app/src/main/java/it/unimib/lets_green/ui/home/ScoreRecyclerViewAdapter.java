package it.unimib.lets_green.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import it.unimib.lets_green.R;

public class ScoreRecyclerViewAdapter extends RecyclerView.Adapter<ScoreRecyclerViewAdapter.ScoreViewHolder> {

    private double hp;
    private double co2;
    private Context context;

    public ScoreRecyclerViewAdapter(Context context, double hp, double co2) {
        this.context = context;
        this.hp = hp;
        this.co2 = co2;
    }

    @NonNull
    @Override
    public ScoreRecyclerViewAdapter.ScoreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.score_item, parent, false);
        return new ScoreViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ScoreRecyclerViewAdapter.ScoreViewHolder holder, int position) {
        int score = (int) (hp - co2);
        holder.scoreTextView.setText("Score: " + score);
    }

    @Override
    public int getItemCount() {
        return 1;
    }


    public class ScoreViewHolder extends RecyclerView.ViewHolder {

        TextView scoreTextView;

        public ScoreViewHolder(@NonNull View itemView) {
            super(itemView);
            scoreTextView = itemView.findViewById(R.id.scoreTextView);
        }
    }
}
