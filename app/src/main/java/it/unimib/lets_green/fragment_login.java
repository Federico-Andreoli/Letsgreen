package it.unimib.lets_green;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;


public class fragment_login extends Fragment {
    Button outlinedButon;
    MainActivity objMyClass = new MainActivity ();
    Activity reference=objMyClass.getActivityReference();

    public static fragment_login newInstance() {

        return new fragment_login();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        outlinedButon= view.findViewById(R.id.outlinedButton2);

        outlinedButon.setOnClickListener(new View.OnClickListener(){
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View arg0) {
                Navigation.findNavController(view).navigate(R.id.register);
            }
        });

        return view;
    }

}
