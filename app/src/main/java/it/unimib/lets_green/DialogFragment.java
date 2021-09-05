package it.unimib.lets_green;

import static it.unimib.lets_green.R.string.loginRequestedDialogueFragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

public class DialogFragment extends AppCompatDialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder= new AlertDialog.Builder(getActivity());
        builder.setTitle(loginRequestedDialogueFragment)
                .setMessage(R.string.textDialogueFragment)
                .setPositiveButton(R.string.login, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
                        navController.navigate(R.id.fragment_login);
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        return builder.create();
    }
}
