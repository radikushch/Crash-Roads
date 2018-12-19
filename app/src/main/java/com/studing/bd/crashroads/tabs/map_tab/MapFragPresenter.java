package com.studing.bd.crashroads.tabs.map_tab;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.GeoApiContext;
import com.google.maps.model.SnappedPoint;
import com.studing.bd.crashroads.R;
import com.studing.bd.crashroads.model.Response;
import com.studing.bd.crashroads.ui.map_tab.MapFragment;

import java.util.ArrayList;

public class MapFragPresenter implements OnMapReadyCallback, Model.OnSaveRouteCallback {
    private static final String[] INITIAL_PERMS = {Manifest.permission.ACCESS_COARSE_LOCATION};
    private static final int INITIAL_REQUEST = 1337;
    private MapFragment mapFragment;
    private Model model;
    private GeoApiContext mContext;
    private GoogleMap gMap;
    private MapView mapView;
    private GoogleMap.OnMapClickListener mapClickListener;
    private Response response;
    private Polyline inputedPolyline;
    private ArrayList<LatLng> way;

    private static final int BAD_QUALITY = 1;
    private static final int LOW_QUALITY = 2;
    private static final int MIDDLE_QUALITY = 3;
    private static final int HIGH_QUALITY = 4;
    private static final int GOOD_QUALITY = 5;

    public MapFragPresenter(MapFragment mapFragment){
        this.mapFragment = mapFragment;
        model = new Model(this);
        way = new ArrayList<>();
    }

    public void init(){
        setGeoLoacation();

    }

    private void setGeoLoacation() {
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
        mContext = new GeoApiContext.Builder().apiKey(mapFragment.getString(R.string.google_maps_web_services_key)).build();
    }

    private void showUserLocation(Location location){
        LatLng userLocation = new LatLng(location.getLatitude(), location.getLongitude());
        CameraPosition cameraPosition = CameraPosition.builder().target(userLocation).zoom(16).build();
        gMap.addMarker(new MarkerOptions().position(userLocation));
        gMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

//    public LatLng[] convert(com.google.maps.model.LatLng route[]){
//        int size = route.length;
//        LatLng convertedRoute[] = new LatLng[size];
//        com.google.maps.model.LatLng point;
//        for(int i = 0; i < size; i++){
//            point = route[i];
//            convertedRoute[i] = new LatLng(point.lat, point.lng);
//        }
//        return convertedRoute;
//    }
//
//    public com.google.maps.model.LatLng[] convert(ArrayList<LatLng> route){
//        int size = route.size();
//        com.google.maps.model.LatLng convertedRoute[] = new com.google.maps.model.LatLng[size];
//        LatLng point;
//        for (int i = 0; i < size; i++){
//            point = route.get(i);
//            convertedRoute[i] = new com.google.maps.model.LatLng(point.latitude, point.longitude);
//        }
//        return convertedRoute;
//    }

//    public ArrayList<LatLng> convert(SnappedPoint snappedPoints[]){
//        int size = snappedPoints.length;
//        ArrayList<LatLng> convertedRoute = new ArrayList<>();
//        SnappedPoint snappedPoint;
//        com.google.maps.model.LatLng location;
//        for(int i = 0; i < size; i++){
//            snappedPoint = snappedPoints[i];
//            location = snappedPoint.location;
//            convertedRoute.add(new LatLng(location.lat, location.lng));
//        }
//        return convertedRoute;
//    }

    public void initMap(){
        mapView = (MapView) mapFragment.getView().findViewById(R.id.map);
        if(mapView != null){
            mapView.onCreate(null);
            mapView.onResume();
            mapView.getMapAsync(this);
        }
    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        MapsInitializer.initialize(mapFragment.getContext());
        gMap = googleMap;
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
    }
    public void buttonHandle(View v) {
        switch (v.getId()) {
            case R.id.add_responce_fab:
                mapFragment.getDoneBtn().setVisibility(View.VISIBLE);
                mapFragment.getCancelBtn().setVisibility(View.VISIBLE);
                if (mapClickListener == null) {
                    mapClickListener = new GoogleMap.OnMapClickListener() {
                        @Override
                        public void onMapClick(LatLng latLng) {
                            way.add(latLng);
                            inputedPolyline = gMap.addPolyline(new PolylineOptions().addAll(way));
                        }
                    };
                } else {
                    mapFragment.getView().setEnabled(true);
                }
                gMap.setOnMapClickListener(mapClickListener);
                break;
            case R.id.action_done:
                mapFragment.getDoneBtn().setVisibility(View.GONE);
                mapFragment.getCancelBtn().setVisibility(View.GONE);
                response = new Response();
                createRateDialog();
                gMap.setOnMapClickListener(null);
                break;
            case R.id.action_cancel:
                gMap.setOnMapClickListener(null);
                //responce.removePoint();
                //inputedPolyline.;
                //implement cancel method in Responce class + invalidate
                break;
        }
    }

    private void createRateDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(mapFragment.getActivity());
        LayoutInflater inflater = mapFragment.getActivity().getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.rating_dialog, null);
        dialogBuilder.setView(dialogView);
        final RatingBar ratingBar = dialogView.findViewById(R.id.rating);
        ratingBar.setRating(3);
        dialogBuilder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                response.setRoute(way);
                response.setMark((int) ratingBar.getRating());
                response.setTime(System.currentTimeMillis());
                //snap to roads
                model.saveRoute(response);
                mapFragment.getView().setEnabled(false);
                dialog.dismiss();
            }
        });
        dialogBuilder.setTitle("Rate road");
        AlertDialog dialog = dialogBuilder.create();
        dialog.show();
    }


    @Override
    public void onRouteSave() {

    }

    @Override
    public void onError(String message) {
        //mapFragment.handleError(message);//what?
    }

}
