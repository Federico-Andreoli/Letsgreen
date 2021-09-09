package it.unimib.lets_green.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

public class UserAdapter1 extends RecyclerView.Adapter<UserAdapter1.UsersHolder> {

    private List<UserFirebase> users;
    private Context context;
    private StorageReference imgReference;

    public UserAdapter1(List<UserFirebase> users, Context context) {
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
        holder.score.setText(users.get(position).getScore().toString());
        imgReference = FirebaseStorage.getInstance().getReferenceFromUrl("gs://lets-green-b9ddf.appspot.com/profile-image/" + users.get(position).getUserId() + ".png");
        if (imgReference != null){
            final long ONE_MEGABYTE = 1024 * 1024; // dimensione massima dell'immagine da scaricare
            imgReference.getBytes(ONE_MEGABYTE).addOnSuccessListener(bytes -> {
                Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                holder.imageProfile.setImageBitmap(bmp);
            }).addOnFailureListener(exception -> {
                // TODO: Handle any errors
            });
        }  else{
            holder.imageProfile.setImageResource(R.drawable.image_profile);
        }
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
