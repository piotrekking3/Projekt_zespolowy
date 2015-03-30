package com.example.mrlukashem.utrudnieniaruchu;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import junit.framework.Assert;

import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by mrlukashem
 */
/*
TODO: Zwinięcie navigation draera po wypełnieniu formularza dodającego nowy marker w celu łatwiejszego zaznaczenia miejsca na mapie.
 */
public class MainActivity extends ActionBarActivity
        implements OnMapReadyCallback, NewMarkerOnMap, GoogleMap.OnMapLongClickListener {

    //map fields
    private GoogleMap gMap;
    private UiSettings uiSettings;
    private final LatLng WROCLAW_RYNEK = new LatLng(51.1056248, 17.0381557);

    //listeners fields
    GoogleMap.OnMapClickListener onMapClickListener;
    GoogleMap.OnMarkerClickListener onMarkerClickListener;
    private ListView.OnItemClickListener navDrawerListListener;

    //drawer fields
    private DrawerLayout drawerLayout;
    private ListView drawerListView;
    private NavDrawerArrayAdapter drawerListAdapter;

    //dialogs
    private Dialog markerContentDialog;
    private CategoriesChoiceDialogFragment chooseCatDialog;
    //ActionBar
    private ActionBar aBar;

    private FragmentManager fragmentManager;

    private boolean isLongClickListening = false;
    private String tempEmailFromNewMarkerForm;
    private String tempContentFromNewMarkerForm;
    private int tempCatIdFromMarkerForm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ObjectsOnMapHandler.objectsOnMapHandler.setObjectsOnMapHandler(gMap, getApplicationContext());
        setContentView(R.layout.activity_main);
        setListeners();
        setActionBar();

        fragmentManager = getSupportFragmentManager();
        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        markerContentDialog = DialogFactory
                .newInstance(DialogFactory.DIALOG_TYPE.MARKER_CONTENT_DIALOG, this);
        setDialogFragments();

        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        drawerListView = (ListView)findViewById(R.id.left_drawer);
        NavDrawerArrayAdapter drawerListAdapter =
                new NavDrawerArrayAdapter(this, R.id.drawer_text_view_list_item);
        drawerListAdapter.makeAndSetItems();

        drawerListView.setOnItemClickListener(navDrawerListListener);
        drawerListView.setAdapter(drawerListAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.clear();
        getMenuInflater().inflate(R.menu.menu_main, menu);
        super.onCreateOptionsMenu(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        gMap = googleMap;
        uiSettings = googleMap.getUiSettings();

        gMap.addMarker(new MarkerOptions()
                .position(WROCLAW_RYNEK)
                .title("Marker"));

        gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(WROCLAW_RYNEK, 14));

        uiSettings.setZoomControlsEnabled(true);
        uiSettings.setMyLocationButtonEnabled(true);
        uiSettings.setMapToolbarEnabled(true);

        setMapListeners();
        ObjectsOnMapHandler.objectsOnMapHandler.setMap(gMap);
    }

    private void setMapListeners() {
        gMap.setOnMapClickListener(onMapClickListener);
        gMap.setOnMarkerClickListener(onMarkerClickListener);
        gMap.setOnMapLongClickListener(this);
    }

    private void setActionBar() {
        aBar = this.getSupportActionBar();
        if(aBar == null)
            return;

        this.aBar.setDisplayHomeAsUpEnabled(true);
        this.aBar.setHomeButtonEnabled(true);
        this.aBar.setDisplayShowTitleEnabled(false);
    }

    private void setDialogFragments() {
        chooseCatDialog = CategoriesChoiceDialogFragment.newInstance();
    }

    private void setListeners() {
        onMapClickListener = new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                //temp
                Toast _toast =
                        Toast.makeText(getApplicationContext(), "kliknięcie na mape", Toast.LENGTH_LONG);
                _toast.show();
            }
        };

        onMarkerClickListener = new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                markerContentDialog.show();
                return true;
            }
        };

        navDrawerListListener = new ListView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> __parent, View __view, int __position, long __id) {
                switch (__position) {
                    /*
                        TODO: Zajęcie się kwestią lepszego tworzenia okienek dialogowych za pomoca fragmentów.
                     */
                    case 0:
                        FragmentTransaction tt = getFragmentManager().beginTransaction();
                        DialogFragment dd = (DialogFragment)getFragmentManager().findFragmentByTag("FormDialog");
                        if(dd != null) {
                            tt.remove(dd);
                        }
                        tt.addToBackStack(null);

                        FormDialogFragment ff = FormDialogFragment.newInstance();
                        tt.add(ff, "FormDialog");
                        tt.commit();
                        break;
                    case 1:

                        break;
                    case 2:

                        break;
                    case 3:
                        FragmentTransaction t = getFragmentManager().beginTransaction();
                        DialogFragment d = (DialogFragment)getFragmentManager().findFragmentByTag("dialog");
                        if(d != null) {
                            t.remove(d);
                        }
                            t.addToBackStack(null);

                            CategoriesChoiceDialogFragment f = CategoriesChoiceDialogFragment.newInstance();
                            t.add(f, "dialog");
                            t.commit();
                        break;
                    case 4:

                        break;

                };
            }
        };
    }

    @Override
    public void setLongClickListener() {
        isLongClickListening = true;
    }

    @Override
    public void setToastMarkerInfo() {
        Toast _text = Toast.makeText(
                this,
                R.string.onLongClickOnMapString,
                Toast.LENGTH_LONG);
        _text.show();
    }

    @Override
    public void setData(String __email, int __category_id, String __content) {
        tempEmailFromNewMarkerForm = __email;
        tempCatIdFromMarkerForm = __category_id;
        tempContentFromNewMarkerForm = __content;
    }

    @Override
    public void onMapLongClick(LatLng __latLng) {
        Log.e("onMapLongClick", "długie kliknięcie na mapę");
        if(!isLongClickListening) {
            return;
        }

        MarkerOptions _options = new MarkerOptions();
        _options.title(tempEmailFromNewMarkerForm);
        _options.position(__latLng);
        ObjectsOnMapHandler.objectsOnMapHandler.addMarker(_options, tempCatIdFromMarkerForm);
        isLongClickListening = false;
    }
}
