package it.unimib.lets_green;

import static android.app.Activity.RESULT_OK;
import static it.unimib.lets_green.Firebase.Autentication.changeEmailAddress;
import static it.unimib.lets_green.Firebase.Autentication.deleteAccount;
import static it.unimib.lets_green.Firebase.Autentication.logOutUser;
import static it.unimib.lets_green.FirestoreDatabase.FirestoreDatabase.TAG;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import it.unimib.lets_green.Firebase.Autentication;
import it.unimib.lets_green.ui.Login.Login;


public class UserProfileFragment extends Fragment {
    private String userEmail;
    private String userName;
    private EditText ciao;
    private EditText newUserEmail;
    private EditText newUserName;
    private Button logOutButton;
    private ImageButton addPhoto;
    private ImageView profilePic;
    private TextView displayUserName;
    private Button changePassword;
    private Button changeEmail;
    private Button changeUserName;
    private Button deleteAccount;
    public static Uri imageUri;
    private static final int PICK_IMAGE = 100;
    private StorageReference storageReference;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_profile, container, false);
        logOutButton = view.findViewById(R.id.logOut);
        addPhoto = view.findViewById(R.id.addPhoto);
        profilePic = view.findViewById(R.id.profile_image);
        displayUserName= view.findViewById(R.id.textemailUser);
        changePassword= view.findViewById(R.id.buttonPassword);
        changeEmail=view.findViewById(R.id.buttonChangeEmail);
        changeUserName=view.findViewById(R.id.buttonChangeUserName);
        newUserEmail=view.findViewById(R.id.textInputEmailChange);
        changeEmail=view.findViewById(R.id.buttonChangeEmail);
        newUserName=view.findViewById(R.id.textInputUserNameChange);
        deleteAccount=view.findViewById(R.id.buttonDeleteAccount);


        storageReference = FirebaseStorage.getInstance().getReference();

        setDisplayUserName();


        newUserName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                userName = newUserName.getText().toString().trim();
                changeUserName.setEnabled(!userName.isEmpty());
            }

            @Override
            public void afterTextChanged(Editable s) {
//
            }
        });

        newUserEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                userEmail=newUserEmail.getText().toString().trim();
                changeEmail.setEnabled(!userEmail.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(userEmail).matches());

            }

            @Override
            public void afterTextChanged(Editable s) {
//
            }
        });
        changeUserName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Autentication.changeDataUserName(newUserName.getText().toString().trim()); //richiama il metodo con il nome utente da inserire
                Toast.makeText(getActivity(), "user name changed", Toast.LENGTH_SHORT).show();
            }
        });

        deleteAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder= new AlertDialog.Builder(getActivity());
                builder.setTitle(R.string.delete_account_alert_title);
                builder.setMessage(R.string.delete_account_alert_message);
                builder.setPositiveButton("yes", new DialogInterface.OnClickListener() // gestione interazione con pulsante yes
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Navigation.findNavController(getActivity(), R.id.nav_host_fragment).navigate(R.id.fragment_login);
                        deleteAccount();
                        logOutUser();
                        Toast.makeText(getActivity(), "user deleted", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setNegativeButton("no", new DialogInterface.OnClickListener() // gestione interazione con pulsante no
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                AlertDialog alertDialog= builder.create(); //creazione alert
                alertDialog.show(); //mostra alert
            }
        });
        changeEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userEmail=newUserEmail.getText().toString().trim();
                changeEmailAddress(userEmail);
                Toast.makeText(getActivity(), "email changed", Toast.LENGTH_SHORT).show();
            }
        });


        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Login.resetPassword(Login.getEmail());  // richiama il metodo per resettare la password
                Toast.makeText(getActivity(), "sand email change password", Toast.LENGTH_SHORT).show();
                Navigation.findNavController(getActivity(), R.id.nav_host_fragment).navigate(R.id.fragment_login);// sposta l'utente nella sezione login
                logOutUser();  // effettua il logout per rieffettuare l'autenticazione
            }
        });

        logOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logOutUser();
                Toast.makeText(getActivity(), "logout effettuato", Toast.LENGTH_SHORT).show();
                Navigation.findNavController(getActivity(), R.id.nav_host_fragment).navigate(R.id.fragment_login);
            }
        });

        getUserImage();

        addPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent openGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(openGallery, 1000);
            }
        });



        return view;
    }

    private void setDisplayUserName() {

        FirebaseFirestore.getInstance().collection("User").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    if (document.getId().equals(Login.getUserID())){
                        displayUserName.setText("Hello, "+ document.getData().get("userName").toString());
                    }
                }
            }
        });
    }



    private void getUserImage() {

        String pathgetImage= "profile-image/"+ Login.getUserID();
        StorageReference imageGet  = storageReference.child(pathgetImage);
        final long ONE_MEGABYTE = 4 * 1024 * 1024;
        imageGet.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                profilePic.setImageBitmap(bmp);
            }
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 1000 && resultCode == RESULT_OK) {

            Uri imageUri = data.getData();
            profilePic.setImageURI(imageUri);
            uploadImageToFirebase(imageUri);
        }


    }

    private void uploadImageToFirebase(Uri uri) {
        String insertImage= "profile-image/"+ Login.getUserID();
        StorageReference fileRef= storageReference.child(insertImage);
        if(uri!=null){
            fileRef.putFile(uri);
        }else {
            Log.d(TAG,"uri is null" );
        }

    }

}
