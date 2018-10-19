package com.studing.bd.crashroads.realtime_database;

import android.content.Context;
import android.util.Log;

public class MapFragPresenter {
    private Model model;
    private Route route;
    public MapFragPresenter(){
        model = new Model();
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
}
