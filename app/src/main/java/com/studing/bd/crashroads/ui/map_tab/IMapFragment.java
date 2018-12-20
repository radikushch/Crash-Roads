package com.studing.bd.crashroads.ui.map_tab;

import android.content.Context;
import android.location.LocationManager;

import com.google.android.gms.maps.MapView;

public interface IMapFragment {
    void handleError(String message);

    void createRateDialog();

    MapView getMapView();

    Context context();

    void setFabEnable();
}
