package it.unimib.lets_green.FirestoreDatabase;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import it.unimib.lets_green.Login;

public class FirestoreDatabase {

    public static final String TAG = " FirestoreDatabase";



    public static void initializeData(String userID) {



        Map<String, Object> defaultData = new HashMap<>();

        defaultData.put("score", 0);

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
