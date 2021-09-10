package it.unimib.lets_green.FirestoreDatabase;


import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.Map;

import it.unimib.lets_green.ui.Login.Login;

public class FirestoreDatabase {

    public static final String TAG = " FirestoreDatabase";



    public static void initializeData(String userID) {


        Map<String, Object> defaultData = new HashMap<>();

        defaultData.put("score", 0);
        defaultData.put("userName", userID);

        FirebaseFirestore.getInstance().collection("User").document(userID).set(defaultData);// inizializzazione score utente
    }

    public static void addPlantToGreenHouse(String namePlant) {
        Map<String, String> addPlant = new HashMap<>();

        addPlant.put("namePlant", namePlant);
        FirebaseFirestore.getInstance().collection("User")
                .document(Login.getUserID())
                .collection("greenHouse")
                .add(addPlant);
    }

    public static void initializeImage(Context context, int drawableId){

        Uri imageUri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE +
                "://" + context.getResources().getResourcePackageName(drawableId)
                + '/' + context.getResources().getResourceTypeName(drawableId)
                + '/' + context.getResources().getResourceEntryName(drawableId) );
        String insertImage= "profile-image/"+ Login.getUserID();
        StorageReference fileRef= FirebaseStorage.getInstance().getReference().child(insertImage);
        if(imageUri!=null){
            fileRef.putFile(imageUri);
        }else {
            Log.d(TAG,"uri is null" );
        }
    }

}
