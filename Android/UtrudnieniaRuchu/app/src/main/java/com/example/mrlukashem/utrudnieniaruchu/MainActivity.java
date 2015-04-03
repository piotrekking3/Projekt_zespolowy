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
import java.util.Objects;
import java.util.zip.Inflater;

/**
 * Created by mrlukashem
 */
/*
TODO: Zwinięcie navigation draera po wypełnieniu formularza dodającego nowy marker w celu łatwiejszego zaznaczenia miejsca na mapie.
 */
public class MainActivity extends ActionBarActivity
        implements OnMapReadyCallback, NewMarkerOnMap {

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

    private String tempEmailFromNewMarkerForm;
    private String tempContentFromNewMarkerForm;
    private int tempCatIdFromMarkerForm;
    private LatLng lastLongClickLatLng;

    //drawer lists elements ids
    private static final int NEW_MARKER = 0;
    private static final int MAP_SET_TERRAIN = 1;
    private static final int MAP_SET_SATATELITE = 2;
    private static final int CATEGORIES_FILTER = 3;
    private static final int CATEGORIES_LIST = 4;
    private static final int OPTIONS = 5;
    private static final int HELP = 6;

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
                    case NEW_MARKER:
                        prepareToNewMarker();
                        break;
                    case MAP_SET_TERRAIN:
                        gMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                        break;
                    case MAP_SET_SATATELITE:
                        gMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                        break;
                    case CATEGORIES_FILTER:
                        showFilterCategoriesDialog();
                        break;
                    case CATEGORIES_LIST:
                        /*
                            TODO: Zrobienie jakiś podstawowych opcji w nowym Activity
                         */
                        break;
                    case OPTIONS:
                        break;
                    case HELP:
                        break;
                    default:
                        break;
                };
            }
        };
    }

    @Override
    public void setLongClickListener() {
        gMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng __latLng) {
                lastLongClickLatLng = __latLng;
                showNewMarkerFormDialog();
                gMap.setOnMapLongClickListener(null);
            }
        });
    }

    @Override
    public void createMarkerFromFormData(String __email, int __category_id, String __content) {
        setFormData(
                Objects.requireNonNull(__email, "Email arg is null"),
                __category_id,
                Objects.requireNonNull(__content, "Content agr is null"));
        addMarkerFromFormData();
    }

    private void prepareToNewMarker() {
        drawerLayout.closeDrawers();
        setLongClickListener();
        setToastMarkerInfo();
    }

    @Override
    public void setToastMarkerInfo() {
        Toast _text = Toast.makeText(
                this,
                R.string.onLongClickOnMapString,
                Toast.LENGTH_LONG);
        _text.show();
    }

    private void setFormData(String __email, int __category_id, String __content) {
        tempEmailFromNewMarkerForm = __email;
        tempCatIdFromMarkerForm = __category_id;
        tempContentFromNewMarkerForm = __content;
    }

    private void addMarkerFromFormData() {
        MarkerOptions _options = new MarkerOptions();
        _options.title(Objects.requireNonNull(tempEmailFromNewMarkerForm, "null email"));
        _options.position(lastLongClickLatLng);
        ObjectsOnMapHandler.objectsOnMapHandler.addMarker(_options, tempCatIdFromMarkerForm);
    }

    private void showNewMarkerFormDialog() {
        FragmentTransaction _f_transaction = getFragmentManager().beginTransaction();
        DialogFragment _f_dialog = (DialogFragment)getFragmentManager().findFragmentByTag("FormDialog");
        if(_f_dialog != null) {
            _f_transaction.remove(_f_dialog);
        }
        _f_transaction.addToBackStack(null);

        FormDialogFragment ff = FormDialogFragment.newInstance();
        _f_transaction.add(ff, "FormDialog");
        _f_transaction.commit();
    }

    private void showFilterCategoriesDialog() {
        FragmentTransaction _f_transaction = getFragmentManager().beginTransaction();
        DialogFragment _f_dialog = (DialogFragment)getFragmentManager().findFragmentByTag("FilterDialog");
        if(_f_dialog != null) {
            _f_transaction.remove(_f_dialog);
        }
        _f_transaction.addToBackStack(null);

        CategoriesChoiceDialogFragment f = CategoriesChoiceDialogFragment.newInstance();
        _f_transaction.add(f, "FilterDialog");
        _f_transaction.commit();
    }
}
