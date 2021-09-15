package it.unimib.lets_green;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.github.dhaval2404.imagepicker.ImagePicker;

public class provaImageProfile extends AppCompatActivity {

    public Button logOutButton;
    public ImageButton addPhoto;
    public ImageView profilePic;
    public static Uri imageUri;
    private static final int PICK_IMAGE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prova_image_profile);

        logOutButton= findViewById(R.id.logOut);
        addPhoto= findViewById(R.id.addPhoto);
        profilePic= findViewById(R.id.profile_image);

        addPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.with(provaImageProfile.this)
//                        .crop()	    			//Crop image(Optional), Check Customization for more option
//                        .compress(1024)			//Final image size will be less than 1 MB(Optional)
//                        .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                        .start();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Uri uri= data.getData();
        profilePic.setImageURI(uri);
    }

}