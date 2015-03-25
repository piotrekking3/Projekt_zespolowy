package com.example.mrlukashem.utrudnieniaruchu;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;


import java.util.ArrayList;

import static android.content.DialogInterface.OnCancelListener;

/**
 * Created by mrlukashem on 25.03.15.
 */
public class CategoriesChoiceDialog {

    //oznaczone elementy listy
    private ArrayList<Integer> selectedItems = new ArrayList<>();
    //listenery
    private DialogInterface.OnMultiChoiceClickListener multiChoiceClickListener;
    private DialogInterface.OnClickListener negativListener;
    private DialogInterface.OnClickListener positivListener;
    private AlertDialog dialog;

    private void setListeners() {
        multiChoiceClickListener = new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                if(isChecked) {
                    selectedItems.add(which);
                }
                else
                if(selectedItems.contains(which)){
                    selectedItems.remove(which);
                }
            }
        };

        negativListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        };

        positivListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        };
    }

    /*
        @param: Context aplikacji
        Konstruktor na bazie buildera tworzy okienko dialogowe z lista.
     */
    public CategoriesChoiceDialog(Context __con) {
        AlertDialog.Builder _builder = new AlertDialog.Builder(__con);

        _builder
                .setTitle(R.string.cat_choice_title)
                .setMultiChoiceItems(R.array.categories, null, multiChoiceClickListener)
                .setPositiveButton(R.string.ok, positivListener)
                .setNegativeButton(R.string.cancel, negativListener);

        dialog = _builder.create();
    }

    /*
        Zwraca referencje na gotowe okienko dialogowe
        @return: Okienko dialogowe
     */
    public AlertDialog getDialog() {
        return dialog;
    }
}
