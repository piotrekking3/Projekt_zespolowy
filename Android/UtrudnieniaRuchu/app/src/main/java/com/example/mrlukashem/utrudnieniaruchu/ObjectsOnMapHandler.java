package com.example.mrlukashem.utrudnieniaruchu;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

import android.widget.Toast;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

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
    private ArrayList<ProblemInstance> markersOnMap;
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

    public Context getContext() {
        return context;
    }

    public GoogleMap getMap() {
        return gMap;
    }

    /*
        Metoda dodająca nowy marker/problem na mapę i sprawdzająca, czy jego kategoria
        nie została wyłączona przez użytkownika.
        @param1 Dane potrzebne do stworzenia nowego utrudnienia
        @param2 marker options
        Po stworzeniu i dodania na mapę i do listy nowego markera/problemu metoda sprawdza
        czy kategoria w jakiej marker się znajduje jest wyświetlana.
        Jeżeli nie to zostaje on wyłączony.
     */
    public void addProblem(ProblemInstance.ProblemData __data){
        if(__data.getCategoryId() >= categoriesStateArray.length
                || __data.getCategoryId() < 0) {
            throw new ArrayIndexOutOfBoundsException("Index poza zakresem tablicy kategorii");
        }
        MarkerOptions _options = new MarkerOptions();
        ProblemInstance _problem;
        Marker _marker;

        _options
                .position(__data.getCords())
                .title(context.getResources().getStringArray(R.array.categories)[__data.getCategoryId()])
                .snippet(__data.getContent())
                .icon(BitmapDescriptorFactory.defaultMarker(getMarkerColorById(__data.getCategoryId())));
        _marker = gMap.addMarker(_options);
        try {
             _problem = ProblemInstance.newInstance(__data, _marker);
        } catch(NullPointerException __e) {
            _marker.remove();
            return;
        }
        markersOnMap.add(_problem);

        if (!categoriesStateArray[_problem.getCategoryId()]) {
            _marker.setVisible(false);
        }
    }

    public void removeProblem(ProblemInstance __problem) {
        int _i = 0;
        for(ProblemInstance p : markersOnMap) {
            if(p.equals(__problem)) {
                break;
            }
            _i++;
        }

        markersOnMap.get(_i).getMarker().remove();
        markersOnMap.remove(_i);
    }

    public void addProblemToServerAndMap(ProblemInstance.ProblemData __data) {
        if (__data.getCategoryId() >= categoriesStateArray.length
                || __data.getCategoryId() < 0) {
            throw new ArrayIndexOutOfBoundsException("Index poza zakresem tablicy kategorii");
        }
        MarkerOptions _options = new MarkerOptions();
        ProblemInstance _problem;
        Marker _marker;

        _options
                .position(__data.getCords())
                .title(context.getResources().getStringArray(R.array.categories)[__data.getCategoryId()])
                .snippet(__data.getContent())
                .icon(BitmapDescriptorFactory.defaultMarker(getMarkerColorById(__data.getCategoryId())));
        _marker = gMap.addMarker(_options);
        try {
            _problem = ProblemInstance.newInstance(__data, _marker);
        } catch (NullPointerException __e) {
            _marker.remove();
            return;
        }

        markersOnMap.add(_problem);

        if (!categoriesStateArray[_problem.getCategoryId()]) {
            _marker.setVisible(false);
        }

        try {
            new Thread(new ProblemToServerSender(_problem)).start();
        } catch (Exception __e) {
            Log.e("addProblemToSAM", __e.toString());
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
        Metoda wyszukująca instancję zadanego problemu po jego markerze
        @param1 marker
        @return znaleziony obiekt lub null w przeciwnym wypadku
     */
    public ProblemInstance findProblemByMarker(Marker __marker) {
        for(ProblemInstance problem : markersOnMap) {
            if(__marker.equals(problem.getMarker())) {
                return problem;
            }
        }

        return null;
    }

    public void refresh() {
        for(ProblemInstance problem : markersOnMap) {
            MarkerOptions _options = new MarkerOptions();
            _options.snippet(problem.getContent());
            _options.title(problem.getCategoryString());
            _options.position(problem.getMarker().getPosition());
            gMap.addMarker(_options);
        }

        refreshMap();
    }

    /*
        Funkcja odpowiedzialna za ustawienie zadanej ketogorii poprzez Id wartości
        true lub false w zależności od drugiego argumentu.
        @param1 Id kategorii
        @param2 nowa wartość true lub false
     */
    private void setCategoryVisible(Integer __index, boolean __setter) {
        for(ProblemInstance p : markersOnMap) {
            if(p.getCategoryId().equals(__index)) {
                p.getMarker().setVisible(__setter);
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

    private float getMarkerColorById(int __id) {
        switch (__id) {
            case 0:
                return BitmapDescriptorFactory.HUE_AZURE;
            case 1:
                return BitmapDescriptorFactory.HUE_BLUE;
            case 2:
                return BitmapDescriptorFactory.HUE_CYAN;
            case 3:
                return BitmapDescriptorFactory.HUE_GREEN;
            case 4:
                return BitmapDescriptorFactory.HUE_MAGENTA;
            case 5:
                return BitmapDescriptorFactory.HUE_ORANGE;
            case 6:
                return BitmapDescriptorFactory.HUE_RED;
            case 7:
                return BitmapDescriptorFactory.HUE_ROSE;
            case 8:
                return BitmapDescriptorFactory.HUE_VIOLET;
            default:
                return BitmapDescriptorFactory.HUE_RED;
        }
    }

    private class ProblemToServerSender implements Runnable {

        private ProblemInstance problemInstance;

        public ProblemToServerSender(ProblemInstance __problem) {
            problemInstance = __problem;
        }

        @Override
        public void run() {
            try {
                new CallAPI
                        .GetLocationByCords(context, problemInstance)
                        .execute(problemInstance.getCords())
                        .get(8, TimeUnit.SECONDS);
            } catch(Exception __e) {
                Log.e("addProblemToSAM", __e.toString());
                Toast.makeText(context, "Nie można nawiązać połączenia. Proszę spróbować później.", Toast.LENGTH_LONG).show();
                return;
            }

                CallAPI.getInstance().addProblem(problemInstance);
        }
    }
}
