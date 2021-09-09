package it.unimib.lets_green.Firebase;

import static it.unimib.lets_green.FirestoreDatabase.FirestoreDatabase.TAG;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import it.unimib.lets_green.Login;

public class Autentication {

    public static void logOutUser(){
        FirebaseAuth.getInstance().signOut();
        Login.logOutSet();
    }
    public static void changeEmailAddress(String email){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        user.updateEmail(email) //imposta la nuova email
        .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG,"email changed");
                        }
                    }
                });
    }
    public static void deleteAccount(){
        StorageReference storageReference= FirebaseStorage.getInstance().getReference();;
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DocumentReference mDocRef = FirebaseFirestore.getInstance().collection("User").document(Login.getUserID());

        StorageReference image  = storageReference.child("profile-image/"+ Login.getUserID());

            image.delete()
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Log.d(TAG, "immage account deleted.");
                            }
                        }
                    });

        user.delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "User account deleted.");
                        }
                    }
                });

        mDocRef.delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "document account deleted.");
                        }
                    }
                });


    }
}