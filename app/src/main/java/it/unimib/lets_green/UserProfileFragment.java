package it.unimib.lets_green;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import it.unimib.lets_green.Firebase.Autentication;


public class UserProfileFragment extends Fragment {
    public Button logOutButton;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_profile, container, false);
        logOutButton= view.findViewById(R.id.logOut);
        logOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Autentication.logOutUser();
                Toast.makeText(getActivity(), "logOut effettuato", Toast.LENGTH_SHORT).show();
                Navigation.findNavController(getActivity(), R.id.nav_host_fragment).navigate(R.id.fragment_login);
            }
        });
        return view;
    }
   }