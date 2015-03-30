package com.example.mrlukashem.utrudnieniaruchu;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by mrlukashem on 26.03.15.
 */

/*
    Klasa informuje ile w danej chwili znajduje się markerów na mapie.
    Filtrująca i wyświetlająca tylko kategorie zadane maską categoriesStateArray.
    Implementująca wzorzec singleton.
 */
public class ObjectsOnMapHandler {
    public static ObjectsOnMapHandler objectsOnMapHandler =
            new ObjectsOnMapHandler();

    private ObjectsOnMapHandler() {}

    /*
        Ustawienie wszystkich pół wymaganych do popranwego funkcjonowania klasy
     */
    private void setFields() {
        Resources _res = context.getResources();
        int _array_size = _res.getInteger(R.integer.categories_array_size);
        categoriesStateArray = new boolean[_array_size];
        for(int i = 0; i < _array_size; i++) {
            categoriesStateArray[i] = true;
        }

        markersOnMap = new ArrayList<>();
    }

    private boolean[] categoriesStateArray;
    private ArrayList<Pair<Marker, Integer>> markersOnMap;
    private GoogleMap gMap;
    private Context context;

    public void setMap(GoogleMap __map) {
        gMap = __map;
    }

    public void setContext(Context __con) {
        context = __con;
    }

    public void setObjectsOnMapHandler(GoogleMap __map, Context __con) {
        gMap = __map;
        context = __con;
        setFields();
    }

    public GoogleMap getMap() {
        return gMap;
    }

    /*
        Metoda dodająca nowy marker na mapę i sprawdzająca, czy jego kategoria
        nie została wyłączona przez użytkownika.
        @param1 Nowy markerOptions
        @param2 Typ - kategoria markera
        Po stworzeniu i dodania na mapę i do listy nowego markera metoda sprawdza
        czy kategoria w jakiej marker się znajduje jest wyświetlana.
        Jeżeli nie to zostaje on wyłączony.
     */
    public void addMarker(MarkerOptions __marker_option, Integer __type){
        Marker _marker = gMap.addMarker(__marker_option);
        markersOnMap.add(new Pair<>(_marker, __type));

        if(__type > 0 && __type < categoriesStateArray.length) {
            if (!categoriesStateArray[__type]) {
                _marker.setVisible(false);
            }
        }
    }

    /*
        Ustawia maskę kategorii za pomoca tablicy wartości bool.
        @param1 Nowa maska
     */
    public void setCategoriesStateArray(boolean[] __state_array) {
        if(__state_array.length != categoriesStateArray.length) {
            return;
        }

        for(int i = 0; i < __state_array.length; i++) {
            categoriesStateArray[i] = __state_array[i];
        }

        refreshMap();
    }

    public boolean[] getCategoriesStateArray() {
        return categoriesStateArray;
    }

    /*
        Funkcja odpowiedzialna za ustawienie zadanej ketogorii poprzez Id wartości
        true lub false w zależności od drugiego argumentu.
        @param1 Id kategorii
        @param2 nowa wartość true lub false
     */
    private void setCategoryVisible(Integer __index, boolean __setter) {
        for(Pair<Marker, Integer> e : markersOnMap) {
            if(e.getSecond().equals(__index)) {
                e.getFirst().setVisible(__setter);
            }
        }
    }

    /*
        Metoda odświeżająca widok na mapie. W tym celu iterujemy po masce kategorii
        i ustawiamy widzialność markerów tylko dla markerów z wartością true.
     */
    private void refreshMap() {
        for(int category = 0; category < categoriesStateArray.length; category++) {
            if(categoriesStateArray[category]) {
                setCategoryVisible(category, true);
            } else {
                setCategoryVisible(category, false);
            }
        }
    }
}
