package com.studing.bd.crashroads.realtime_database;

import android.graphics.Point;

import java.util.ArrayList;
import java.util.List;

public class Route {
    public List<WayPoint> getWay() {
        return way;
    }

    public List<WayPoint> way = new ArrayList<>();

    public void addWaypoint(WayPoint point){
        way.add(point);
    }
}
