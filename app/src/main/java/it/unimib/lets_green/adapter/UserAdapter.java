package it.unimib.lets_green.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import java.util.List;

import it.unimib.lets_green.R;
import it.unimib.lets_green.UserFirebase;

public class UserAdapter extends FirestoreRecyclerAdapter<UserFirebase, UserAdapter.UserHolder> {

    private List<UserFirebase> users;


    public UserAdapter(@NonNull FirestoreRecyclerOptions<UserFirebase> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull UserHolder holder, int position, @NonNull UserFirebase model) {

    }

    @NonNull
    @Override
    public UserHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_leaderboard, parent, false);
        return new UserHolder(v);
    }

    public class UserHolder extends RecyclerView.ViewHolder {

        TextView textViewusername;
        TextView textViewscore;


        public UserHolder(@NonNull View itemView) {

            super(itemView);
            textViewusername = (TextView)itemView.findViewById(R.id.profileName);
            textViewscore =(TextView)itemView.findViewById(R.id.profileScore);
        }
    }
}
