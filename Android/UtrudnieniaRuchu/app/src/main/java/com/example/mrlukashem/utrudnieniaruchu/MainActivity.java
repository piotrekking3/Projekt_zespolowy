package com.example.mrlukashem.utrudnieniaruchu;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
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

public class MainActivity extends FragmentActivity
        implements OnMapReadyCallback {

    //app fields
    private Context context;

    //map fields
    private GoogleMap gMap;
    private UiSettings uiSettings;
    private LatLng WROCLAW_RYNEK = new LatLng(51.1056248, 17.0381557);

    //listeners fields
    GoogleMap.OnMapClickListener onMapClickListener;
    GoogleMap.OnMarkerClickListener onMarkerClickListener;

    //drawer fields
    private DrawerLayout drawerLayout;
    private ListView drawerListView;

    //dialogs
    private Dialog markerContentDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setListeners();
        context = getApplicationContext();

        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);

        markerContentDialog = DialogFactory
                .newInstance(DialogFactory.DIALOG_TYPE.MARKER_CONTENT_DIALOG, this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
                .position(new LatLng(51, 17))
                .title("Marker"));

        gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(WROCLAW_RYNEK, 14));
        gMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);

        uiSettings.setZoomControlsEnabled(true);
        uiSettings.setMyLocationButtonEnabled(true);
        uiSettings.setMapToolbarEnabled(true);

        setMapListeners();
    }

    private void setMapListeners() {
        gMap.setOnMapClickListener(onMapClickListener);
        gMap.setOnMarkerClickListener(onMarkerClickListener);
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
    }
}
