package it.unimib.lets_green.FirestoreDatabase;

import android.util.Log;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import it.unimib.lets_green.Login;
import it.unimib.lets_green.ui.home.HomeFragment;

public class FirestoreDatabase {

    public static final String TAG = " FirestoreDatabase";


    public static void initializeDataGreenHouse(String userID) {

        ArrayList<String> plantedPlant = new ArrayList<String>();

        DocumentReference mDocRef = FirebaseFirestore.getInstance().collection("User").document(userID);

        Map<String, ArrayList> data = new HashMap<>();
        data.put("plantedPlant", plantedPlant);
        mDocRef.set(data).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {

                Log.d(TAG, "document has been saved");
            }
        });
    }
    public static void initializeDataPath(String userID) {


        DocumentReference mDocRef = FirebaseFirestore.getInstance().collection("User").document(userID);

    }

    public static void addPlantToGreenHouse(String namePlant, String hp) {
        Map<String, String> addPlant = new HashMap<>();
        addPlant.put("namePlant", namePlant);
        addPlant.put("hp", hp);
        FirebaseFirestore.getInstance().collection("User")
                .document(Login.getUserID())
                .collection("greenHouse")
                .add(addPlant);
    }

    public static void updateHp(double totalHp) {
        FirebaseFirestore.getInstance().collection("User")
                .document(Login.getUserID())
                .update("totalHp", totalHp);
    }

    public static void updateDate() {
        FirebaseFirestore.getInstance().collection("User")
                .document(Login.getUserID())
                .update("lastUpdate", LocalDate.now().minusDays(1).toString());
    }

    public static void updateScore(int score) {
        FirebaseFirestore.getInstance().collection("User")
                .document(Login.getUserID())
                .update("score", score);
    }

//   public static void modifyData(String UserID, ArrayList<String> greenHousePlant,ArrayList<String> path){
//        Map<String, ArrayList> ChangedData = new HashMap<>();
//
//        ChangedData.clear();
//        ChangedData.put("changedplant", greenHousePlant);
//        ChangedData.put("changedpath", path);
//
//       FirebaseFirestore.getInstance().collection("User").document(UserID)
//               .set(ChangedData)
//               .addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void unused) {
//                        Log.d(TAG, "document has been changed");
//                    }
//               });
//}
}
