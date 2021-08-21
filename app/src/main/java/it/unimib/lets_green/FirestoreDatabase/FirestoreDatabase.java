package it.unimib.lets_green.FirestoreDatabase;

import android.util.Log;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firestore.v1.WriteResult;

import java.util.HashMap;
import java.util.Map;

public class FirestoreDatabase {
public static final String TAG=" FirestoreDatabase";

public static void saveData(String ciao, String caio) {

    DocumentReference mDocRef= FirebaseFirestore.getInstance().collection("User").document("dataUser");
    Map<String, Object> data = new HashMap<>();
    data.put("first", ciao);
    data.put("last", caio);
    data.put("born", 1815);
   mDocRef.set(data).addOnSuccessListener(new OnSuccessListener<Void>() {
       @Override
       public void onSuccess(Void unused) {
           Log.d(TAG, "document has been saved");
       }
   });
}
   public static void modifyData(){
        Map<String, Object> ChangedData = new HashMap<>();
        ChangedData.put("first", "giovanni");
        ChangedData.put("last", "peppino");
        ChangedData.put("born", 1990);

       FirebaseFirestore.getInstance().collection("User").document("dataUser")
               .set(ChangedData)
               .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d(TAG, "document has been changed");
                    }
               });
   }

}
