package it.unimib.lets_green.FirestoreDatabase;

import android.util.Log;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FirestoreDatabase {

public static final String TAG=" FirestoreDatabase";




    public static void initializeData(String userID) {

        ArrayList<String> plantedPlant=new ArrayList<String>();

        DocumentReference mDocRef= FirebaseFirestore.getInstance().collection("User").document(userID);

        Map<String, ArrayList> data = new HashMap<>();
        data.put("plantedPlant", plantedPlant);
        mDocRef.set(data).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {

                Log.d(TAG, "document has been saved");
            }
        });
    }

   public static void modifyData(String UserID, ArrayList<String> greenHousePlant,ArrayList<String> path){
        Map<String, ArrayList> ChangedData = new HashMap<>();

        ChangedData.clear();
        ChangedData.put("changedplant", greenHousePlant);
        ChangedData.put("changedpath", path);

       FirebaseFirestore.getInstance().collection("User").document(UserID)
               .set(ChangedData)
               .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d(TAG, "document has been changed");
                    }
               });
   }
}
