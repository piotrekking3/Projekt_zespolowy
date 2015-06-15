package com.example.mrlukashem.utrudnieniaruchu;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import java.util.ArrayList;

/**
 * Created by mrlukashem on 07.05.15.
 */

/*
    Okienko dialogowe służące do informacji o błędach w połączeniu np. Błąd logowania.
 */
public class CantLogInDialogFragment extends DialogFragment {

    //błąd połączenia
    private boolean connectionError = false;

    //błąd logowania
    private boolean logInConnectionError = true;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder _builder = new AlertDialog.Builder(getActivity());

        if(connectionError) {
            _builder
                    .setTitle(getResources().getString(R.string.logInConnectionError))
                    .setMessage(getResources().getString(R.string.logInConnectionErrorTitle));
        }
        else
        if(logInConnectionError){
            _builder
                    .setTitle(getResources().getString(R.string.logInErrorTitle))
                    .setMessage(getResources().getString(R.string.logInError));
        }

        _builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface __dialog, int __which) {
                __dialog.dismiss();
            }
        });

        return  _builder.create();
    }

    /*
        Odpowiedni setter służący do przekierowania logiki konstrukcji okna na błąd połączenia.
     */
    public void setConnectionError() {
        connectionError = true;
        logInConnectionError = false;
    }

    /*
        Odpowiedni setter służący do przekierowania logiki konstrukcji okna na błąd logowania.
     */
    public void setLogInConnectionError() {
        logInConnectionError = true;
        connectionError = false;
    }
}
