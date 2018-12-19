package com.studing.bd.crashroads.tabs.map_tab;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.studing.bd.crashroads.R;
import com.studing.bd.crashroads.model.Route;
import com.studing.bd.crashroads.model.WayPoint;
import com.studing.bd.crashroads.ui.map_tab.MapFragment;

public class MapFragPresenter implements Model.OnSaveRouteCallback {
    private static final String[] INITIAL_PERMS = {Manifest.permission.ACCESS_COARSE_LOCATION};
    private static final int INITIAL_REQUEST = 1337;
    private Model model;
    private Route route;
    private MapFragment mapFragment;

    public MapFragPresenter(MapFragment mapFragment){
        this.mapFragment = mapFragment;
        model = new Model(this);
    }

    public void init(){
        LocationManager locationManager = (LocationManager) mapFragment.getActivity()
                .getSystemService(Context.LOCATION_SERVICE);
        LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                showUserLocation(location);
            }
            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };
        if(ContextCompat.checkSelfPermission(mapFragment.getActivity(),
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            mapFragment.requestPermissions(INITIAL_PERMS, INITIAL_REQUEST);
        }else{
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                    0 , 0, locationListener);
        }
        mContext = new GeoApiContext.Builder().apiKey(fragment.getString(R.string.google_maps_web_services_key)).build();
    }

    public void save(){
        model.saveRoute(route);
        Log.d("flag", "called");
    }

    @Override
    public void onRouteSave() {

    }

    @Override
    public void onError(String message) {
        mapFragment.handleError(message);
    }
}
