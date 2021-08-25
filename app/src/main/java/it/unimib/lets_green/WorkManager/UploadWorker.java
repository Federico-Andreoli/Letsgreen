package it.unimib.lets_green.WorkManager;


import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Constraints;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import it.unimib.lets_green.FirestoreDatabase.FirestoreDatabase;
import it.unimib.lets_green.Login;

public class UploadWorker extends Worker {

    private static final String TAG = "test work manager";
    public UploadWorker(Context context, WorkerParameters params) {

        super(context,params);

    }

    @NonNull
    @Override
    public Result doWork() {
        Log.d(TAG, "il work manager funziona");
        FirestoreDatabase.modifyData(Login.getUserID());
        return Result.retry();
    }
}
