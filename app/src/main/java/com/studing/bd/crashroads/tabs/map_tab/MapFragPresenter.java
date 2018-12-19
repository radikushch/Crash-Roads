package com.studing.bd.crashroads.tabs.map_tab;

import android.util.Log;

import com.studing.bd.crashroads.model.Route;
import com.studing.bd.crashroads.model.WayPoint;
import com.studing.bd.crashroads.ui.map_tab.MapFragment;

public class MapFragPresenter implements Model.OnSaveRouteCallback {
    private Model model;
    private Route route;
    private MapFragment mapFragment;

    public MapFragPresenter(MapFragment mapFragment){
        this.mapFragment = mapFragment;
        model = new Model(this);
        route = new Route();
        WayPoint point1 = new WayPoint(2.543, 45.875);
        WayPoint point2 = new WayPoint(4.938, -78.654);
        route.addWaypoint(point1);
        route.addWaypoint(point2);
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
