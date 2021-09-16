package it.unimib.lets_green.ui.Login;

import android.annotation.SuppressLint;
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
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import it.unimib.lets_green.MainActivity;
import it.unimib.lets_green.R;

public class Login extends Fragment {

    private static boolean is_logged=false;
    private static final String TAG = "login successful";
    EditText userEmail, userPassword;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    Button registerButon;
    Button loginButon;
    Button resetPassword;
    MainActivity objMyClass = new MainActivity();
    static FirebaseUser user=null;
    static String UserID= null;

    public static Login newInstance() {
        return new Login();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        userEmail = view.findViewById(R.id.textInputEmailLogin);
        userPassword = view.findViewById(R.id.textInputPasswordLogin);
        registerButon = view.findViewById(R.id.outlinedButton2);
        loginButon = view.findViewById(R.id.containedButton);
        resetPassword = view.findViewById(R.id.resetPassword);

        // impostazione titolo action bar
        ((MainActivity) getActivity()).setActionBarTitle(getString(R.string.profile));

        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                Navigation.findNavController(view).navigate(R.id.navigation_home);// gestione personalizzata pulsante back
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(getActivity(), callback);

        if (is_logged){
            NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
            navController.navigate(R.id.userProfileFragment);
        }

        registerButon.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View arg0) {
                Navigation.findNavController(view).navigate(R.id.register);
            }
        });

        loginButon.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View arg0) {
                LoginAccount();
            }
        });

        resetPassword.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View arg0) {
                String email = userEmail.getText().toString().trim();
                String password = userPassword.getText().toString().trim();
                if(email.isEmpty()) {  /*controllo che la mail non sia vuota*/
                    Toast.makeText(getActivity(), R.string.insertEmail, Toast.LENGTH_SHORT).show();
                }else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){ /*verifica se la mail è ben formulata*/
                    Toast.makeText(getActivity(), R.string.InsertValidEmail, Toast.LENGTH_SHORT).show();
                }else {
                    resetPassword(email);
                    Toast.makeText(getActivity(),  R.string.sentEmailToChangePassword, Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    private void LoginAccount() {
        String email = userEmail.getText().toString().trim();
        String password = userPassword.getText().toString().trim();

        if(email.isEmpty()){  /*controllo che la mail non sia vuota*/
            Toast.makeText(getActivity(), R.string.insertEmail, Toast.LENGTH_SHORT).show();
        }else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){ /*verifica se la mail è ben formulata*/
            Toast.makeText(getActivity(), R.string.InsertValidEmail, Toast.LENGTH_SHORT).show();
        }else if(password.isEmpty()){   /*verifica che la password non sia vuota*/
            Toast.makeText(getActivity(), R.string.insertValidPassword, Toast.LENGTH_SHORT).show();
        }else{
            LoginFirebase(email,password);

        }
    }

    public void LoginFirebase(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // login effettuato con successo
                            user = mAuth.getCurrentUser(); // ottiene riferimento all'oggetto user
                            UserID =user.getUid().toString(); // ottiene l'id dell'utente
                            updateUI(user); // se l'utente é loggato cambia il flag is_logged
                            changeUI();//carica il fragment del profilo utente
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(getActivity(), R.string.authenticationFailed,
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });

    }

    public static void resetPassword(String email) {
        FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "Email sent");
                        }
                    }
                });
    }

    public static String getEmail(){
        user = FirebaseAuth.getInstance().getCurrentUser();
        return user.getEmail();
    }

    public static void updateUI(FirebaseUser user) {
        if(user!=null){
            is_logged=true;
        }
    }

    public void changeUI(){

        Navigation.findNavController(getActivity(), R.id.nav_host_fragment).navigate(R.id.userProfileFragment);
        Toast.makeText(getActivity(), R.string.loginDone, Toast.LENGTH_SHORT).show();
    }

    public static boolean getIs_logged() {
        return is_logged;
    }

    public static void logOutSet() {
        is_logged=false;
    }

    public static void setUserID(String userID) {
        UserID = userID;
    }

    public static FirebaseUser getUser() {
        return user;
    }

    public static String getUserID() {
        return UserID;
    }

    public static void setUser(FirebaseUser User) {
        user = User;
    }

}