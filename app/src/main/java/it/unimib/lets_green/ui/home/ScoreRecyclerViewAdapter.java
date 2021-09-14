package it.unimib.lets_green.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import it.unimib.lets_green.FirestoreDatabase.FirestoreDatabase;
import it.unimib.lets_green.R;
import it.unimib.lets_green.ui.Login.Login;

public class ScoreRecyclerViewAdapter extends RecyclerView.Adapter<ScoreRecyclerViewAdapter.ScoreViewHolder> {

    private double hp;
    private Context context;
    private double score = 0;

    public ScoreRecyclerViewAdapter(Context context, double hp) {
        this.context = context;
        this.hp = hp;
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
        FirebaseFirestore.getInstance()
                .collection("User")
                .document(Login.getUserID())
                .get()
                .addOnCompleteListener(task -> {
                   if (task.isSuccessful()) {
                       DocumentSnapshot document = task.getResult();
                       if (document.exists()) {
                           score = Double.parseDouble(document.get("score").toString());
                           score += hp;
                           FirestoreDatabase.updateScore(score);
                           holder.scoreTextView.setText("score: "+ (int) score);
                           if(Double.parseDouble(document.get("score").toString())< 0){
                               holder.scoreTextView.setBackgroundColor(ContextCompat.getColor(context, R.color.gray));

                           }else{
                               holder.scoreTextView.setBackgroundColor(ContextCompat.getColor(context,R.color.green));

                           }
                       }
                   }
                });
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