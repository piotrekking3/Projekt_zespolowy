package com.example.mrlukashem.utrudnieniaruchu;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.FragmentManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import com.google.android.gms.maps.model.LatLng;

import java.security.Provider;

/**
 * Created by mrlukashem on 07.05.15.
 */
public class GPSTracker extends Service implements LocationListener {

    private static final long MIN_TIME_BW_UPDATES = 10;
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 1000 * 60;
    private boolean isGPSEnabled;
    private boolean isNetworkEnabled;
    private boolean isPassivProviderEnabled;
    private Context context;

    private LocationManager locationManager;
    private Location location;
    private double latidute;
    private double longitude;

    public GPSTracker(Context __context) {
        context = __context;
        try {
            getLocation();
        } catch(LocationException __l_exc) {
            Log.e("Loc exc", __l_exc.toString());
        }
    }

    public Location getLocation()
        throws LocationException {
        try {
            locationManager = (LocationManager)context.getSystemService(LOCATION_SERVICE);
            isPassivProviderEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

            if(!isPassivProviderEnabled) {
                throw new LocationException("GPS provider is not enabled");
            } else {
                if(locationManager != null) {
                    locationManager.requestLocationUpdates(
                            LocationManager.PASSIVE_PROVIDER,
                            MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                    location = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
                    if(location != null) {
                        return location;
                    }
                }
            }

            isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if(!isGPSEnabled && !isNetworkEnabled) {
                throw new LocationException("GPS provider is not enabled");
            } else {
                if(isGPSEnabled) {
                    locationManager.requestLocationUpdates(
                            LocationManager.NETWORK_PROVIDER,
                            MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES, this);

                    if(locationManager != null) {
                        location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                        if(location != null) {
                            return location;
                        }
                    }
                }
                else {
                    locationManager.requestLocationUpdates(
                            LocationManager.NETWORK_PROVIDER,
                            MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES, this);

                    if (locationManager != null) {
                        location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        if(location != null) {
                            return location;
                        }
                    }
                }
            }
        } catch(Exception __exc) {
            Log.e("GPS error", __exc.toString());
        }

        return null;
    }

    public double getLastLatitude() {
        if(location != null) {
            latidute = location.getLatitude();
        }

        return latidute;
    }

    public double getLastLongitude() {
        if(location != null) {
            longitude = location.getLongitude();
        }

        return longitude;
    }

    public void stopUsingGPS() {
        if(locationManager != null) {
            locationManager.removeUpdates(GPSTracker.this);
        }
    }

    public LatLng getLastLatLng() {
        if(location != null) {
            latidute = location.getLatitude();
            longitude = location.getLongitude();
        }

        return new LatLng(latidute, longitude);
    }

    @Override
    public void onLocationChanged(Location __location) {
    }

    @Override
    public void onStatusChanged(String __provider, int __status, Bundle __extras) {
    }

    @Override
    public void onProviderEnabled(String __provider) {
    }

    @Override
    public void onProviderDisabled(String __provider) {
    }

    @Override
    public IBinder onBind(Intent __intent) {
        return null;
    }

    public void showErrorWindow(FragmentManager __fragmentManager) {
        __fragmentManager
                .beginTransaction()
                .add(new NoGpsEnableDialogFragment(), "GpsDialog")
                .commit();
    }

    public class LocationException extends Exception {
        public LocationException() {
            super();
        }

        public LocationException(String __msg) {
            super(__msg);
        }
    }
}
