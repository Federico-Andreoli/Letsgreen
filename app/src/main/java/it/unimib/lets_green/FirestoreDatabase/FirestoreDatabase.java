package it.unimib.lets_green.FirestoreDatabase;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import it.unimib.lets_green.ui.Login.Login;

public class FirestoreDatabase {

    public static final String TAG = " FirestoreDatabase";



    public static void initializeData(String userID) {

            Map<String, Object> defaultData = new HashMap<>();
            defaultData.put("score", 0);
            defaultData.put("userName", userID);
            defaultData.put("lastUpdate", LocalDate.now().toString());

            FirebaseFirestore.getInstance().collection("User").document(userID).set(defaultData);// inizializzazione score utente
        }

    public static void addPlantToGreenHouse(String namePlant, String hp) {
        Map<String, String> addPlant = new HashMap<>();
        addPlant.put("namePlant", namePlant);
        addPlant.put("hp", hp);
        FirebaseFirestore.getInstance().collection("User")
                .document(Login.getUserID())
                .collection("greenHouse")
                .add(addPlant); // aggiunge la pianta passata come argomento alla serra
    }

    public static void updateHp(double totalHp) {
        FirebaseFirestore.getInstance().collection("User")
                .document(Login.getUserID())
                .update("totalHp", totalHp); //aggiorna la vita della pianta
    }

    public static void updateDate() {
        FirebaseFirestore.getInstance().collection("User")
                .document(Login.getUserID())
                .update("lastUpdate", LocalDate.now().toString());// aggiorna ultimo accesso
    }

    public static void updateScore(double score) {
        FirebaseFirestore.getInstance().collection("User")
                .document(Login.getUserID())
                .update("score", score);// aggiorna il punteggio dell'utente
    }

    public static void addPlantToScore(double hp) {
        FirebaseFirestore.getInstance().collection("User")
                .document(Login.getUserID())
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (task.isSuccessful()) {
                            double score = (Double.parseDouble(document.get("score").toString()) + hp);
                            FirebaseFirestore.getInstance().collection("User")
                                    .document(Login.getUserID())
                                    .update("score", score);
                        }
                    }
                });
    }

    public static void subtractPlantFromScore(double hp) {
        FirebaseFirestore.getInstance().collection("User")
                .document(Login.getUserID())
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (task.isSuccessful()) {
                            double score = (Double.parseDouble(document.get("score").toString()) - hp);
                            FirebaseFirestore.getInstance().collection("User")
                                    .document(Login.getUserID())
                                    .update("score", score);
                        }
                    }
                });
    }

    public static void initializeImage(Context context, int drawableId){

        Uri imageUri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE +
                "://" + context.getResources().getResourcePackageName(drawableId)
                + '/' + context.getResources().getResourceTypeName(drawableId)
                + '/' + context.getResources().getResourceEntryName(drawableId) ); //ottiene l'uri del file drowable image_profile
        String insertImage= "profile-image/"+ Login.getUserID();
        StorageReference fileRef= FirebaseStorage.getInstance().getReference().child(insertImage);
        if(imageUri!=null){
            fileRef.putFile(imageUri);// carica nello storage l'immagine utente di default
        }else {
            Log.d(TAG,"uri is null" );
        }
    }

}
