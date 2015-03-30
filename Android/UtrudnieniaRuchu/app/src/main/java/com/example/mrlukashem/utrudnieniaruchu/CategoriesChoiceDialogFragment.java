package com.example.mrlukashem.utrudnieniaruchu;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by mrlukashem on 27.03.15.
 */
public class CategoriesChoiceDialogFragment extends DialogFragment {

    //oznaczone elementy listy
    private ArrayList<Integer> selectedItems;
    //listenery
    private DialogInterface.OnMultiChoiceClickListener multiChoiceClickListener;
    private DialogInterface.OnClickListener negativListener;
    private DialogInterface.OnClickListener positivListener;

    public static CategoriesChoiceDialogFragment newInstance() {
        CategoriesChoiceDialogFragment _instance =
                new CategoriesChoiceDialogFragment();

        return _instance;
    }

    /*
        Metoda buduje okienko dialogowe za pomoca builder oraz pobiera
        aktualny stan tablicy kategorii w celu wyswietlenia go w liscie
        @param zachowany poprzedni stan
        @return gotowe okienko dialogowe
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        setListeners();
        selectedItems = new ArrayList<>();
        AlertDialog.Builder _builder = new AlertDialog.Builder(getActivity());
        boolean[] _get_state_array = ObjectsOnMapHandler.objectsOnMapHandler.getCategoriesStateArray();
        boolean[] _state_array = new boolean[_get_state_array.length];
        for(int i = 0; i < _state_array.length; i++) {
            _state_array[i] = _get_state_array[i];
            if(_state_array[i]) {
                selectedItems.add(i);
            }
        }

        _builder
                .setTitle(R.string.cat_choice_title)
                .setMultiChoiceItems(
                        R.array.categories,
                        _state_array,
                        multiChoiceClickListener)
                .setPositiveButton(R.string.ok, positivListener)
                .setNegativeButton(R.string.cancel, negativListener);

        return  _builder.create();
    }

    /*
        Metoda ustawia wszystkie obiekty sluchaczy niezbedne do prawidlowego
        funkcjonowania okienka.
     */
    private void setListeners() {
        //ustawienie sluchacza, ktory jest uruchamiany podczas odznaczania
        //poszczegolnych checkboxow
        multiChoiceClickListener = new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                if (isChecked) {
                    selectedItems.add(which);
                } else if (selectedItems.contains(which)) {
                    selectedItems.remove(Integer.valueOf(which));
                }
            }
        };

        negativListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        };

        //Sluchacz na klikniecie przycisku potwierdzenia wybranych kategorii
        positivListener = new DialogInterface.OnClickListener() {
            /*
                @param1 okienko dialogowe
                @param2 numer przycisku
             */
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //pobranie wielkosci tablicy przechowujacej kategorie
                int _size = getResources().getInteger(R.integer.categories_array_size);
                //nowa tablica stanow
                boolean[] _state_table = new boolean[_size];
                //przypisanie wszedziedzie stanu wylaczonego
                for(int i = 0; i < _state_table.length; i++) {
                    _state_table[i] = false;
                }
                //Dla kazdego indeksu odpowiadajacego kategorii i znajdujacemu sie w tablicy
                //odhaczonych przypisuje sie wartosc true w tablicy stanow
                for (Integer selected : selectedItems) {

                    if (selected >= 0 && selected < _size) {
                        _state_table[selected] = true;
                    }
                }

                //uaktualnianie tablicy stanow
                ObjectsOnMapHandler.objectsOnMapHandler.setCategoriesStateArray(_state_table);
                dialog.dismiss();
            }
        };
    }
}
