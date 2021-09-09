package it.unimib.lets_green.FirestoreDatabase;


import com.google.firebase.firestore.FirebaseFirestore;

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

}
