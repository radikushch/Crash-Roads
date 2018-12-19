package com.studing.bd.crashroads.tabs.map_tab;

import android.view.View;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.studing.bd.crashroads.Utils;
import com.studing.bd.crashroads.model.Route;
import com.studing.bd.crashroads.ui.map_tab.IMapFragment;

import java.util.ArrayList;

public class MapPresenter implements OnMapReadyCallback, MapModel.OnSaveRouteCallback, IMapPresenter {

    private GoogleMap googleMap;

    private IMapFragment mapFragment;
    private MapModel mapModel;

    private ArrayList<LatLng> way;

    public MapPresenter(IMapFragment mapFragment){
        this.mapFragment = mapFragment;
        mapModel = new MapModel(this);
        way = new ArrayList<>();
        initMap();
    }

//    private void showUserLocation(Location location){
//        LatLng userLocation = new LatLng(location.getLatitude(), location.getLongitude());
//        CameraPosition cameraPosition = CameraPosition.builder().target(userLocation).zoom(16).build();
//        gMap.addMarker(new MarkerOptions().position(userLocation));
//        gMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
//    }

    private void initMap(){
        MapView mapView = mapFragment.getMapView();
        if(mapView != null){
            mapView.onCreate(null);
            mapView.onResume();
            mapView.getMapAsync(this);
        }
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        MapsInitializer.initialize(mapFragment.context());
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        this.googleMap = googleMap;
    }

    @Override
    public void onRouteSave() {
        mapFragment.handleError("Road was added");
    }

    @Override
    public void onError(String message) {
        mapFragment.handleError(message);
    }

    @Override
    public void enableRouteMarking() {
        googleMap.setOnMapClickListener(latLng -> {
            way.add(latLng);
            googleMap.addPolyline(new PolylineOptions().addAll(way));
        });
    }

    @Override
    public void disableRouteMarking() {
        googleMap.setOnMapClickListener(null);
        mapFragment.createRateDialog();
    }

    @Override
    public void saveRoute(int rating) {
        Route route = new Route();
        route.setRoute(way);
        route.setMark(rating);
        route.setTime(System.currentTimeMillis());
        googleMap.addPolyline(new PolylineOptions().addAll(way).color(Utils.getPolylineColor(rating)));
        mapModel.saveRoute(route);
        way.clear();
    }
}
