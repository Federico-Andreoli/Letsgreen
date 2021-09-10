package it.unimib.lets_green.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import it.unimib.lets_green.FirestoreDatabase.FirestoreDatabase;
import it.unimib.lets_green.R;
import it.unimib.lets_green.ui.Login.Login;


public class Register extends Fragment {
    private static final String TAG = "NewsFragment";
    private FirebaseAuth mAuth;
    EditText userEmail, userPassword, confirmPassword;
    Button registerUser;
    String email,password;
    static String UserID= null;



    public static Register newInstance() {

        return new Register();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment__register, container, false);

        mAuth = FirebaseAuth.getInstance();
        userEmail = view.findViewById(R.id.textInputEmail);
        userPassword = view.findViewById(R.id.textInputPassword1);
        confirmPassword = view.findViewById(R.id.textInputPassword2);
        registerUser= view.findViewById(R.id.containedButton);

        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                Navigation.findNavController(view).navigate(R.id.navigation_notifications);
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(getActivity(), callback);

        registerUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startUserRegistration();/*richiama metodo per la registrazione dei dati*/
            }
        });



        return view;

}

    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            reload();
        }
    }

        private void startUserRegistration() {

        email = userEmail.getText().toString().trim();
        password = userPassword.getText().toString().trim();
        String password2 = confirmPassword.getText().toString().trim();


        if(email.isEmpty()){  /*controllo che la mail non sia vuota*/
            Toast.makeText(getActivity(), "inserisci mail", Toast.LENGTH_SHORT).show();
        }else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){ /*verifica se la mail è ben formulata*/
            Toast.makeText(getActivity(), "inserire un'email valida", Toast.LENGTH_SHORT).show();
        }else if(password.isEmpty()){   /*verifica che la password non sia vuota*/
            Toast.makeText(getActivity(), "inserisci una password valida", Toast.LENGTH_SHORT).show();
        }else if(password.length()<8){   /*verifica che la password sia almeno di 8 caratteri*/
            Toast.makeText(getActivity(), "password troppo corta", Toast.LENGTH_SHORT).show();
        }else if(password.compareTo(password2)!=0){ /*verifica che le password sono uguali*/
            Toast.makeText(getActivity(), "le password sono diverse", Toast.LENGTH_SHORT).show();
        }else{
            Log.d(TAG, "registrazione effettuata");
            createAccount(email,password);

        }
    }

    private void createAccount(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            UserID =user.getUid().toString();
                            FirestoreDatabase.initializeData(user.getUid().toString());
                            setDefaultImageProfile();
                            taskSuccessful(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(getActivity(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void setDefaultImageProfile(){
        FirestoreDatabase.initializeImage(getContext(), R.drawable.image_profile);
    }


    private void taskSuccessful(FirebaseUser user) {
        if (user != null) {
            mAuth.signInWithEmailAndPassword(email, password);
            Navigation.findNavController(getActivity(), R.id.nav_host_fragment).navigate(R.id.userProfileFragment);
            Login.updateUI(user);
            Login.setUserID(UserID);
        }
    }
    private void reload(){

    }
}