package it.unimib.lets_green.WorkManager;


import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import java.util.ArrayList;

public class UploadWorker extends Worker {

    private static final String TAG = "test work manager";
    public UploadWorker(Context context, WorkerParameters params) {

        super(context,params);

    }

    @NonNull
    @Override
    public Result doWork() {

        ArrayList<String> plantedPlant=new ArrayList<String>();
        ArrayList<String> path=new ArrayList<String>();

        Log.d(TAG, "il work manager funziona");
//        FirestoreDatabase.modifyData(Login.getUserID(),plantedPlant,path);
        return Result.retry();
    }
}
