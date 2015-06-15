package com.example.mrlukashem.utrudnieniaruchu;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by mrlukashem on 06.05.15.
 */

/*
    Klasa okienka dialogowe służąca do zarejestrowania nowego użytkownika w serwisie.
 */
public class RegistrationDialogFragment extends DialogFragment {

    //Referencje do pól tekstowych w okienku dialogowym
    private EditText emailEditText;
    private EditText loginEditText;
    private EditText passwordEditText;

    /*
        metoda zwracająca nową instancję okienka
        @return nowa instancja okienka
     */
    public static RegistrationDialogFragment newInstance() {
        RegistrationDialogFragment _instance = new RegistrationDialogFragment();
        return _instance;
    }

    /*
        Defaultowa nadpisana metoda. Budujemy nowe okienko, przypisujemy wszystkie pola.
        @return instancja okna dialogowego
     */
    @Override
    public Dialog onCreateDialog(Bundle __saved_instance_state) {
        AlertDialog.Builder _builder = new AlertDialog.Builder(getActivity());
        LayoutInflater _inflate = getActivity().getLayoutInflater();
        View _my_view = _inflate.inflate(R.layout.registration_dialog, null);

        _builder.setView(_my_view)
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface __dialog, int __which) {
                        dismiss();
                    }
                })
                .setPositiveButton(R.string.ok, null)
                .setTitle(getResources().getString(R.string.register_info));

        emailEditText = (EditText)_my_view.findViewById(R.id.emailTextEdit);
        passwordEditText = (EditText)_my_view.findViewById(R.id.passwordEditText);

        return _builder.create();
    }

    /*
        Wywoływana przy pokazywaniu okienka dialogowego.
        Metoda pobiera referencje do przycisku "ok" - potwierdzającego rejestrację
        w celu przypisania owemu przycikowi nowego słuchacza, który nie będzie
        z klasy DialogInterface. Dzięki temu po kliknięciu "ok" okienko się nie zamknie
        jeżeli walidacja emaila przebiegnie negatywnie.
     */
    @Override
    public void onStart() {
        super.onStart();

        AlertDialog _dialog = (AlertDialog)getDialog();
        Button _positiv_button = _dialog.getButton(AlertDialog.BUTTON_POSITIVE);

        _positiv_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!Patterns.EMAIL_ADDRESS.matcher(emailEditText.getText()).matches()) {
                    emailEditText.setError("Niepoprawny email!");
                } else {
                    UserManager.getInstance().adduser(
                            emailEditText.getText().toString(),
                            passwordEditText.getText().toString());
                    //TODO: jezeli sie nie powiedzie to toast?


                    dismiss();
                    //TODO: Może jakiś Toast w stylu: Dodano użytkownika / rejestracja przebiegła pomyślnie, a może zrobić to w loginActivity
                }
            }
        });
    }
}
