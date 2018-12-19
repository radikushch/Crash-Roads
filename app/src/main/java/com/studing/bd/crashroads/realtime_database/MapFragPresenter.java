package com.studing.bd.crashroads.realtime_database;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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
import com.google.maps.RoadsApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.SnappedPoint;
import com.studing.bd.crashroads.R;

import java.io.IOException;
import java.util.ArrayList;

public class MapFragPresenter implements OnMapReadyCallback{
    private static final String[] INITIAL_PERMS = {Manifest.permission.ACCESS_COARSE_LOCATION};
    private static final int INITIAL_REQUEST = 1337;

    private MapFragment fragment;
    private Model model;
    private GeoApiContext mContext;
    private GoogleMap gMap;
    private MapView mapView;
    private GoogleMap.OnMapClickListener mapClickListener;
    private Responce responce;
    private Polyline inputedPolyline;
    private ArrayList<LatLng> way = new ArrayList<>();
    private WriteTask wt;
    private RateDialog rateDialog;

    public MapFragPresenter(MapFragment fragment){
        this.fragment = fragment;
        model = new Model();
    }
    public void init(){
        LocationManager locationManager = (LocationManager) fragment.getActivity()
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
        if(ContextCompat.checkSelfPermission(fragment.getActivity(),
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            fragment.requestPermissions(INITIAL_PERMS, INITIAL_REQUEST);
        }else{
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                    0 , 0, locationListener);
        }
        mContext = new GeoApiContext.Builder().apiKey(fragment.getString(R.string.google_maps_web_services_key)).build();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        MapsInitializer.initialize(fragment.getContext());
        gMap = googleMap;
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
    }

    public void buttonHandle(View v){
        switch (v.getId()){
            case R.id.add_responce_fab:
                fragment.getDoneBtn().setVisibility(View.VISIBLE);
                fragment.getCancelBtn().setVisibility(View.VISIBLE);
                if(mapClickListener == null){
                    mapClickListener = new GoogleMap.OnMapClickListener() {
                        @Override
                        public void onMapClick(LatLng latLng) {
                            way.add(latLng);
                            inputedPolyline = gMap.addPolyline(new PolylineOptions().addAll(way));
                        }
                    };
                }else {
                    fragment.getView().setClickable(true);
                }
                gMap.setOnMapClickListener(mapClickListener);
                break;
            case R.id.action_done:
                fragment.getDoneBtn().setVisibility(View.GONE);
                fragment.getCancelBtn().setVisibility(View.GONE);
                responce = new Responce();
                rateDialog = new RateDialog(fragment.getActivity(), responce);
                rateDialog.show();
                break;
            case R.id.action_cancel:
                //responce.removePoint();
                //inputedPolyline.;
                //implement cancel method in Responce class + invalidate
                break;
        }
    }

    public void showUserLocation(Location location){
        LatLng userLocation = new LatLng(location.getLatitude(), location.getLongitude());
        CameraPosition cameraPosition = CameraPosition.builder().target(userLocation).zoom(16).build();
        gMap.addMarker(new MarkerOptions().position(userLocation));
        gMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    public LatLng[] convert(com.google.maps.model.LatLng route[]){
        int size = route.length;
        LatLng convertedRoute[] = new LatLng[size];
        com.google.maps.model.LatLng point;
        for(int i = 0; i < size; i++){
            point = route[i];
            convertedRoute[i] = new LatLng(point.lat, point.lng);
        }
        return convertedRoute;
    }

    public com.google.maps.model.LatLng[] convert(ArrayList<LatLng> route){
        int size = route.size();
        com.google.maps.model.LatLng convertedRoute[] = new com.google.maps.model.LatLng[size];
        LatLng point;
        for (int i = 0; i < size; i++){
            point = route.get(i);
            convertedRoute[i] = new com.google.maps.model.LatLng(point.latitude, point.longitude);
        }
        return convertedRoute;
    }

    public ArrayList<LatLng> convert(SnappedPoint snappedPoints[]){
        int size = snappedPoints.length;
        ArrayList<LatLng> convertedRoute = new ArrayList<>();
        SnappedPoint snappedPoint;
        com.google.maps.model.LatLng location;
        for(int i = 0; i < size; i++){
            snappedPoint = snappedPoints[i];
            location = snappedPoint.location;
            convertedRoute.add(new LatLng(location.lat, location.lng));
        }
        return convertedRoute;
    }

    public void initMap(){
        mapView = (MapView) fragment.getView().findViewById(R.id.map);
        if(mapView != null){
            mapView.onCreate(null);
            mapView.onResume();
            mapView.getMapAsync(this);
        }
    }

    class WriteTask extends AsyncTask<Void, Void, Void>{
        com.google.maps.model.LatLng path[];
        SnappedPoint snappedPoints[];
        @Override
        protected void onPreExecute() {
            path = convert(way);
            snappedPoints = null;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Log.d("flag", "before snapToRoads");
                snappedPoints = RoadsApi.snapToRoads(mContext, true, path).await();
                Log.d("flag", "after snapToRoads");
            } catch (ApiException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            ArrayList<LatLng> route = new ArrayList<>();
            route = convert(snappedPoints);
            responce.setRoute(route);
            model.saveRoute(responce);
            return  null;
        }
    }

    class RateDialog extends Dialog {
        private static final int BAD_QUALITY = 1;
        private static final int LOW_QUALITY = 2;
        private static final int MIDDLE_QUALITY = 3;
        private static final int HIGH_QUALITY = 4;
        private static final int GOOD_QUALITY = 5;
        private int quality;
        private Responce responce;
        public RateDialog(@NonNull Context context, Responce responce) {
            super(context);
            this.responce = responce;
            quality = MIDDLE_QUALITY;
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.rating_dialog);
            setCancelable(true);
            final TextView rateTV = findViewById(R.id.rate);
            RatingBar ratingBar = findViewById(R.id.rating);
            ratingBar.setRating(quality);
            ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                @Override
                public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                    int rate = (int) rating;
                    switch (rate){
                        case BAD_QUALITY:
                            rateTV.setText(R.string.bad_quality);
                            quality = BAD_QUALITY;
                            break;
                        case LOW_QUALITY:
                            rateTV.setText(R.string.low_quality);
                            quality = LOW_QUALITY;
                            break;
                        case MIDDLE_QUALITY:
                            rateTV.setText(R.string.middle_quality);
                            quality = MIDDLE_QUALITY;
                            break;
                        case HIGH_QUALITY:
                            rateTV.setText(R.string.high_quality);
                            quality = HIGH_QUALITY;
                            break;
                        case GOOD_QUALITY:
                            rateTV.setText(R.string.good_quality);
                            quality = GOOD_QUALITY;
                            break;
                    }
                }
            });
            Button updateButton = findViewById(R.id.update_rating);
            updateButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    responce.setMark(quality);
                    wt = new WriteTask();//change to local
                    wt.execute();
                    inputedPolyline.remove();
                    fragment.getView().setClickable(false);
                    dismiss();
                }
            });
        }
    }

}
