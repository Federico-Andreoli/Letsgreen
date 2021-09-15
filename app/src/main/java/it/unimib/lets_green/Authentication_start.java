package it.unimib.lets_green;

import android.app.Application;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import it.unimib.lets_green.ui.Login.Login;

public class Authentication_start extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if(user!=null){
            Login.setUser(user);
            Login.updateUI(user);
            Login.setUserID(user.getUid());
        }
    }
}
