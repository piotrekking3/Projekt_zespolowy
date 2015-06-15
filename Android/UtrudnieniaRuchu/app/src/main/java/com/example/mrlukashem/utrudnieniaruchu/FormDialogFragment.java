package com.example.mrlukashem.utrudnieniaruchu;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;

import java.util.ArrayList;

/**
 * Created by mrlukashem on 28.03.15.
 */
public class FormDialogFragment extends DialogFragment
        implements Spinner.OnItemSelectedListener {

    private Dialog instance;
    private View customView;
    private DialogInterface.OnClickListener onPositivClickListener;
    private DialogInterface.OnClickListener onNegativClickListener;
    private Spinner spinner;
    private ArrayAdapter<String> spinnerAdapter;
    private int selectedItem;
    private final int MIN_SIZE = 5;

    public static FormDialogFragment newInstance() {
        FormDialogFragment _instance = new FormDialogFragment();
        return _instance;
    }

    /*
        Nadpisana metoda wywolywana przy tworzeniu okienka dialogowe.
        Tworzymy klasę builder w celu ustawienia wszystkich opcji typu:
        dynamiczne załadowanie layoutu, ustawienia przycisków oraz tytułu.
        Na końcu ustawiamy nasz Spinner wyświetlający listę wszystkich kategorii.
        @param Ostatni zachowany stan obiektu
        @return Gotowe okienko dialogowe
     */
    @Override
    public Dialog onCreateDialog(Bundle __saved_instance_state) {
        AlertDialog.Builder _builder = new AlertDialog.Builder(getActivity());
        LayoutInflater _inflater = getActivity().getLayoutInflater();
        setListener();

        customView = _inflater.inflate(R.layout.new_marker_form, null);
        _builder
                .setView(customView)
                .setPositiveButton(R.string.ok, onPositivClickListener)
                .setNegativeButton(R.string.cancel, onNegativClickListener)
                .setTitle(R.string.form_title);

        instance = _builder.create();
        setSpinner();
        return instance;
    }

    /*
        Metoda tworzy obiekty słuchaczy i przypisuje je referencją, które
        są zdefiniowane w polach.
     */
    private void setListener() {
        /*
            Słuchacz odpowiedzialny za odebranie komunikatu o nacisnięciu przycisku ok.
            Następnie pobiera wszystkie dane z pół tekstowych i spinnera. Z kolei potem
            zawęża aktywność do interfejsu odpowiedzialnego za dodanie nowego markera do mapy.
            Wywoływane są metody służące do wyczekiwania na długie kliknięcie na mapie,
            przesyłanie danych do aktywności(z formularza),
            wyświetlenie wiadomości klasą toast.
         */
        onPositivClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                EditText _content = (EditText)customView.findViewById(R.id.newMarkerFormContentEditText);
                CheckBox _check_box = (CheckBox)customView.findViewById(R.id.currentPosCheckBox);

                if(_content.getText().toString().length() > MIN_SIZE) {
                    NewMarkerOnMap _setter = (NewMarkerOnMap) getActivity();
                    _setter.createMarkerFromFormData(
                            _check_box.isChecked(),
                            selectedItem,
                            _content.getText().toString()
                    );
                } else {
                    Toast
                            .makeText(
                                    getActivity().getApplicationContext(),
                                    "Nie można dodać utrudnienia - źle wypełniony formularz",
                                    Toast.LENGTH_LONG)
                            .show();
                }

                dismiss();
            }
        };

        onNegativClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dismiss();
            }
        };
    }

    /*
        metoda służy do ustawienia spinera i połączenia go z jego adapterem
        przechowywującym listę kategorii.
     */
    private void setSpinner() {
        try {
            spinner = (Spinner) customView.findViewById(R.id.categoriesSpinner);
            spinnerAdapter = new ArrayAdapter<>(
                    getActivity(),
                    android.R.layout.simple_spinner_item,
                    getResources().getStringArray(R.array.categories));
            spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(spinnerAdapter);
            spinner.setOnItemSelectedListener(this);
        } catch(Exception __e) {
            Log.e("setSpinnerMethodExc", __e.toString());
            dismiss();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> __parent, View __view, int __position, long __id) {
        selectedItem = __position;
    }

    @Override
    public void onNothingSelected(AdapterView<?> __parent) {

    }
}
