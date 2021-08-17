package it.unimib.lets_green;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class Register extends Fragment {

    EditText userEmail, userPassword, confirmPassword;
    Button registerUser;

    public static Register newInstance() {

        return new Register();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment__register, container, false);
        return view;

        userEmail = view.findViewById(R.id.textInputEmail);
        userPassword = view.findViewById(R.id.textInputPassword1);
        confirmPassword = view.findViewById(R.id.textInputPassword2);
        registerUser= view.findViewById(R.id.containedButton);

        registerUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startUserRegistration();/*richiama metodo per la registrazione dei dati*/
            }
        });

}

    private void startUserRegistration() {

        String email = userEmail.getText().toString().trim();
        String password = userPassword.getText().toString().trim();
        String password2 = confirmPassword.getText().toString().trim();

        if(email.isEmpty()){  /*controllo che la mail non sia vuota*/
            Toast.makeText(this, "inserisci mail", Toast.LENGTH_SHORT).show();
        }else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){ /*verifica se la mail è ben formulata*/
            Toast.makeText(this, "inserire un'email valida", Toast.LENGTH_SHORT).show();
        }else if(password.isEmpty()){   /*verifica che la password non sia vuota*/
            Toast.makeText(this, "inserisci una password valida", Toast.LENGTH_SHORT).show();
        }else if(password.length()<6){   /*verifica che la password sia almeno di 6 caratteri*/
            Toast.makeText(this, "password troppo corta", Toast.LENGTH_SHORT).show();
        }else if(password.compareTo(password2)!=0){ /*verifica che le password sono uguali*/
            Toast.makeText(this, "le password sono diverse", Toast.LENGTH_SHORT).show();
        }else{



        }
    }
}