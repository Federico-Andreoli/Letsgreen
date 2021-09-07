package it.unimib.lets_green;

import static android.app.Activity.RESULT_OK;
import static it.unimib.lets_green.FirestoreDatabase.FirestoreDatabase.TAG;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import it.unimib.lets_green.Firebase.Autentication;


public class UserProfileFragment extends Fragment {
    public Button logOutButton;
    public ImageButton addPhoto;
    public ImageView profilePic;
    public TextView displayMail;
    public static Uri imageUri;
    private static final int PICK_IMAGE = 100;
    private StorageReference storageReference;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_profile, container, false);
        logOutButton = view.findViewById(R.id.logOut);
        addPhoto = view.findViewById(R.id.addPhoto);
        profilePic = view.findViewById(R.id.profile_image);
        displayMail= view.findViewById(R.id.textemailUser);
        storageReference = FirebaseStorage.getInstance().getReference();

        displayMail.setText("Hello, "+Login.getEmail());
//        Login.getEmail();
        logOutButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Autentication.logOutUser();
                Toast.makeText(getActivity(), "logOut effettuato", Toast.LENGTH_SHORT).show();
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

    private void getUserImage() {
        String pathgetImage= "profile image/"+ Login.getUserID();
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
        String insertImage= "profile image/"+ Login.getUserID();
        StorageReference fileRef= storageReference.child(insertImage);
        if(uri!=null){
            fileRef.putFile(uri);
        }else {
            Log.d(TAG,"uri is null" );
        }

    }
}
