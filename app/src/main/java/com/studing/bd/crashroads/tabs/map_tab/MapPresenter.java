package com.studing.bd.crashroads.tabs.map_tab;

import android.util.Log;
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
import java.util.List;

public class MapPresenter implements OnMapReadyCallback, MapModel.OnSaveRouteCallback, IMapPresenter {

    private static final String TAG = "routes";
    private GoogleMap googleMap;

    private IMapFragment mapFragment;
    private MapModel mapModel;

    private List<LatLng> way;
    private List<Polyline> polylines;

    public MapPresenter(IMapFragment mapFragment){
        this.mapFragment = mapFragment;
        mapModel = new MapModel(this);
        way = new ArrayList<>();
        polylines = new ArrayList<>();
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
        mapModel.loadAllRoutes();
    }

    @Override
    public void onRouteLoad(Route route) {
        for (int i = 0; i < route.getRoute().size(); i++) {
             googleMap.addPolyline(new PolylineOptions().addAll(route.getRoute()).color(Utils.getPolylineColor(route.getMark())));
        }
    }

    @Override
    public void onError(String message) {
        mapFragment.handleError(message);
    }

    @Override
    public void onRoadSave() {
        way.clear();
    }

    @Override
    public void enableRouteMarking() {
        googleMap.setOnMapClickListener(latLng -> {
            way.add(latLng);
            polylines.add(googleMap.addPolyline(new PolylineOptions().addAll(way)));
        });
    }

    @Override
    public void disableRouteMarking() {
        if(way.size() > 2) {
            mapFragment.createRateDialog();
        }
        googleMap.setOnMapClickListener(null);
    }

    @Override
    public void saveRoute(int rating) {
        Route route = new Route();
        route.setRoute(way);
        route.setMark(rating);
        route.setTime(System.currentTimeMillis());
        for (int i = 0; i < polylines.size(); i++) {
            polylines.get(i).setColor(Utils.getPolylineColor(rating));
        }
        googleMap.addPolyline(new PolylineOptions().addAll(way).color(Utils.getPolylineColor(rating)));
        mapModel.saveRoute(route);
    }

    @Override
    public void removeAllPolylines() {
        for (int i = 0; i < polylines.size(); i++) {
            polylines.get(i).remove();
        }
        polylines.clear();
        way.clear();
        googleMap.setOnMapClickListener(null);
        mapFragment.setFabEnable();
    }
}
