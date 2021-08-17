package it.unimib.lets_green.database;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import it.unimib.lets_green.R;

public class database_management {
 /*   FirebaseAuth mAuth;
    FirebaseFirestore firestore;
    CollectionReference reference;
    EditText userEmail, userPassword, confirmPassword;
    Button registerUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        userEmail = findViewById(R.id.registerMail);
        userPassword = findViewById(R.id.registerPassword);
        confirmPassword = findViewById(R.id.confirmPassword);
        registerUser= findViewById(R.id.register);
        registerUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startUserRegistration();/*richiama metodo per la registrazione dei dati
            }
        });
    }*/

   /* private void startUserRegistration() {
        String email=userEmail.getText().toString().trim(); /*creazione della stringa da text box
        String password=userPassword.getText().toString().trim();
        String password2=confirmPassword.getText().toString().trim();

        if(email.isEmpty()){  /*controllo che la mail non sia vuota
            Toast.makeText(this, "inserisci mail", Toast.LENGTH_SHORT).show();
        }else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){ /*verifica se la mail è ben formulata
            Toast.makeText(this, "inserire un'email valida", Toast.LENGTH_SHORT).show();
        }else if(password.isEmpty()){   /*verifica che la password non sia vuota
            Toast.makeText(this, "inserisci una password valida", Toast.LENGTH_SHORT).show();
        }else if(password.length()<6){   /*verifica che la password sia almeno di 6 caratteri
            Toast.makeText(this, "password troppo corta", Toast.LENGTH_SHORT).show();
        }else if(password.compareTo(password2)!=0){ /*verifica che le password sono uguali
            Toast.makeText(this, "le password sono diverse", Toast.LENGTH_SHORT).show();
        }else{                                      /*inserimento credenziali firestore
            mAuth.createUserWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult> () {
                @Override
                public void onSuccess(AuthResult authResult) {
                    FirebaseUser user= authResult.getUser();
                    assert user != null;
                    reference = firestore.collection(user.getUid());
                    Map<String, String> userData=new HashMap<>();
                    userData.put ("email", email );
                    reference.add(userData).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Toast.makeText(RegisterActivity.this, "user regstrato con successo", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(RegisterActivity.this, "mail non registrata", Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(RegisterActivity.this, "impossibile registrarsi", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            });
        }

    }
}*/
}
