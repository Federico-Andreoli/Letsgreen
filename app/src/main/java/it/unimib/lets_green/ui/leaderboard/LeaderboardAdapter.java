package it.unimib.lets_green.ui.leaderboard;

import static it.unimib.lets_green.FirestoreDatabase.FirestoreDatabase.TAG;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

import it.unimib.lets_green.R;
import it.unimib.lets_green.UserFirebase;

public class LeaderboardAdapter extends RecyclerView.Adapter<LeaderboardAdapter.UsersHolder> {

    private List<UserFirebase> users;
    private Context context;
    private StorageReference imgReference;

    public LeaderboardAdapter(List<UserFirebase> users, Context context) {
        this.users = users;
        this.context = context;
    }

    @NonNull
    @Override
    public UsersHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.row_leaderboard, parent, false);
        return  new UsersHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull UsersHolder holder, int position) {
        holder.username.setText(users.get(position).getUsername());
        holder.score.setText(String.valueOf((int) users.get(position).getScore()));
        // scaricamento e settaggio immagine
        imgReference = FirebaseStorage.getInstance().getReferenceFromUrl("gs://lets-green-b9ddf.appspot.com/profile-image/" + users.get(position).getUserId());
            Log.d(TAG, users.get(position).getUserId());
            final long ONE_MEGABYTE = 4 * 1024 * 1024; // dimensione massima dell'immagine da scaricare
            imgReference.getBytes(ONE_MEGABYTE).addOnSuccessListener(bytes -> {
                Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                holder.imageProfile.setImageBitmap(bmp);
            }).addOnFailureListener(exception -> {
                Log.d(TAG, "image failure");
            });
        }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class UsersHolder extends RecyclerView.ViewHolder {
        TextView username;
        TextView score;
        ImageView imageProfile;

        public UsersHolder(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.profileName);
            score = itemView.findViewById(R.id.profileScore);
            imageProfile = itemView.findViewById(R.id.profileImg);
        }
    }

}