package it.unimib.lets_green.Firebase;

import com.google.firebase.auth.FirebaseAuth;

import it.unimib.lets_green.Login;

public class Autentication {

    public static void logOutUser(){
        FirebaseAuth.getInstance().signOut();
        Login.logOutSet();
    }
}