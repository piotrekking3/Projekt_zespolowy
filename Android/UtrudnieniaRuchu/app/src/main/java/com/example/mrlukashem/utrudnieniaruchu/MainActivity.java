package com.example.mrlukashem.utrudnieniaruchu;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
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
TODO: Settingsy!
 */
public class MainActivity extends ActionBarActivity
        implements OnMapReadyCallback, NewMarkerOnMap {

    //map fields
    private GoogleMap gMap;
    private MapFragment mapFragment;
    private UiSettings uiSettings;
    private final LatLng WROCLAW_RYNEK = new LatLng(51.1056248, 17.0381557);

    //listeners fields
    private GoogleMap.OnMapClickListener onMapClickListener;
    private GoogleMap.OnMarkerClickListener onMarkerClickListener;
    private GoogleMap.OnMapLongClickListener longClickListener;
    private ListView.OnItemClickListener navDrawerListListener;

    //drawer fields
    private DrawerLayout drawerLayout;
    private ListView drawerListView;
    private ActionBarDrawerToggle drawerToggle;
    private NavDrawerArrayAdapter drawerListAdapter;

    //dialogs
    private Dialog markerContentDialog;
    private CategoriesChoiceDialogFragment chooseCatDialog;
    //ActionBar
    private ActionBar aBar;

    private FragmentManager fragmentManager;

    //new marker form data
    private String tempEmailFromNewMarkerForm;
    private String tempContentFromNewMarkerForm;
    private int tempCatIdFromMarkerForm;
    private LatLng lastLongClickLatLng;

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
        enableListeners();
        setActionBar();

        mapFragment = MapFragment.newInstance();
        FragmentTransaction fragmentTransaction =
                getFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.content_frame, mapFragment);
        fragmentTransaction.commit();
        mapFragment.getMapAsync(this);

        markerContentDialog = DialogFactory
                .newInstance(DialogFactory.DIALOG_TYPE.MARKER_CONTENT_DIALOG, this);
        setDialogFragments();

        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        drawerListView = (ListView)findViewById(R.id.left_drawer);
        drawerListAdapter =
                new NavDrawerArrayAdapter(this, R.id.drawer_text_view_list_item);
        drawerListAdapter.makeAndSetItems();

        drawerListView.setOnItemClickListener(navDrawerListListener);
        drawerListView.setAdapter(drawerListAdapter);

        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
                R.string.app_name, R.string.app_name);

        drawerLayout.setDrawerListener(drawerToggle);
        aBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#0099CC")));

    }

    @Override
    protected void onPostCreate(Bundle __saved_instance_state) {
        super.onPostCreate(__saved_instance_state);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration __newConfig) {
        super.onConfigurationChanged(__newConfig);
        drawerToggle.onConfigurationChanged(__newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu __menu) {
        __menu.clear();
        getMenuInflater().inflate(R.menu.menu_main, __menu);
        super.onCreateOptionsMenu(__menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem __item) {
        int id = __item.getItemId();

        if (id == R.id.action_settings) {
            showPreferenceScreen();
            return true;
        }
        else
        if(drawerToggle.onOptionsItemSelected(__item)){
            return true;
        }
        else
        if(id == android.R.id.home) {
            FragmentTransaction fragmentTransaction =
                    getFragmentManager().beginTransaction();
            fragmentTransaction.add(R.id.content_frame, mapFragment);
            fragmentTransaction.commit();

            drawerToggle.setDrawerIndicatorEnabled(true);
            mapFragment.getMapAsync(this);
            ObjectsOnMapHandler.objectsOnMapHandler.refresh();
            return true;
        }
        return super.onOptionsItemSelected(__item);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        gMap = googleMap;
        uiSettings = googleMap.getUiSettings();
        gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(WROCLAW_RYNEK, 14));

        uiSettings.setZoomControlsEnabled(true);
        uiSettings.setMyLocationButtonEnabled(true);
        uiSettings.setMapToolbarEnabled(true);

        setMapListeners();
        setCustomInfoWindow();
        ObjectsOnMapHandler.objectsOnMapHandler.setMap(gMap);
    }

    private void setMapListeners() {
        gMap.setOnMapClickListener(onMapClickListener);
        gMap.setOnMarkerClickListener(onMarkerClickListener);
    }

    private void setCustomInfoWindow() {
        gMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker __marker) {
                return null;
            }

            @Override
            public View getInfoContents(final Marker __marker) {
                LayoutInflater _inflater = getLayoutInflater();
                View _custom_view = _inflater.inflate(R.layout.window_info_content, null);
                TextView _content = (TextView)_custom_view.findViewById(R.id.contentWindowInfoTextView);
                _content.setText(__marker.getSnippet());
                TextView _title = (TextView)_custom_view.findViewById(R.id.titleWindowInfoTextView);
                _title.setText(__marker.getTitle());
                //TODO: wyświetlanie większej porcji informacji w oknie dialogowym! Czy tak jest okej?

                return _custom_view;
            }
        });

        gMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker __marker) {
                ProblemInstance _problem =
                        ObjectsOnMapHandler.objectsOnMapHandler.findProblemByMarker(__marker);
                if(_problem != null) {
                    showMarkerContentDialog(_problem);
                }
            }
        });
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

    private void enableListeners() {
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
                marker.showInfoWindow();
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
                        showPreferenceScreen();
                        break;
                    case HELP:

                        break;
                    default:
                        break;

                };
            }
        };

        longClickListener = new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng __latLng) {
                lastLongClickLatLng = __latLng;
                showNewMarkerFormDialog();
                gMap.setOnMapLongClickListener(null);
            }
        };
    }

    public void enableLongClickListener() {
        gMap.setOnMapLongClickListener(longClickListener);
    }

    @Override
    public void createMarkerFromFormData(String __email, int __category_id, String __content) {
        setFormData(
                Objects.requireNonNull(__email, "Email arg is null"),
                __category_id,
                Objects.requireNonNull(__content, "Content agr is null"));
        addMarkerFromFormData();
    }

    @Override
    public void showToastMarkerInfo() {
        Toast _text = Toast.makeText(
                this,
                R.string.onLongClickOnMapString,
                Toast.LENGTH_LONG);
        _text.show();
    }

    private void prepareToNewMarker() {
        drawerLayout.closeDrawers();
        enableLongClickListener();
        showToastMarkerInfo();
    }

    private void setFormData(String __email, int __category_id, String __content) {
        tempEmailFromNewMarkerForm = __email;
        tempCatIdFromMarkerForm = __category_id;
        tempContentFromNewMarkerForm = __content;
    }

    private void addMarkerFromFormData() {
        ProblemInstance.ProblemData _data =
                ProblemInstance.createProblemData(
                        tempContentFromNewMarkerForm,
                        tempEmailFromNewMarkerForm,
                        tempCatIdFromMarkerForm,
                        lastLongClickLatLng);
        ObjectsOnMapHandler.objectsOnMapHandler.addProblem(_data);
    }

    private void showPreferenceScreen() {
        drawerToggle.setDrawerIndicatorEnabled(false);
        getFragmentManager().beginTransaction()
                .remove(mapFragment)
                .commit();
        getFragmentManager().beginTransaction()
                .replace(R.id.content_frame, new PreferenceScreen())
                .commit();
        drawerLayout.closeDrawers();
    }

    private void showNewMarkerFormDialog() {
        FragmentTransaction _f_transaction = getFragmentManager().beginTransaction();
        DialogFragment _f_dialog = (DialogFragment)getFragmentManager().findFragmentByTag("FormDialog");
        if(_f_dialog != null) {
            _f_transaction.remove(_f_dialog);
        }
        _f_transaction.addToBackStack(null);

        FormDialogFragment _f = FormDialogFragment.newInstance();
        _f_transaction.add(_f, "FormDialog");
        _f_transaction.commit();
    }

    private void showFilterCategoriesDialog() {
        FragmentTransaction _f_transaction = getFragmentManager().beginTransaction();
        DialogFragment _f_dialog = (DialogFragment)getFragmentManager().findFragmentByTag("FilterDialog");
        if(_f_dialog != null) {
            _f_transaction.remove(_f_dialog);
        }
        _f_transaction.addToBackStack(null);

        CategoriesChoiceDialogFragment _f = CategoriesChoiceDialogFragment.newInstance();
        _f_transaction.add(_f, "FilterDialog");
        _f_transaction.commit();
    }

    private void showMarkerContentDialog(ProblemInstance __problem) {
        FragmentTransaction _f_transaction = getFragmentManager().beginTransaction();
        DialogFragment _f_dialog = (DialogFragment)getFragmentManager().findFragmentByTag("MarkerContentDialog");
        if(_f_dialog != null) {
            _f_transaction.remove(_f_dialog);
        }
        _f_transaction.addToBackStack(null);

        MarkerContentDialogFragment _f = MarkerContentDialogFragment.newInstance(__problem);
        _f_transaction.add(_f, "FilterDialog");
        _f_transaction.commit();
    }
}
