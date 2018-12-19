package com.studing.bd.crashroads.realtime_database;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class Responce {
    public ArrayList<LatLng> getRoute() {
        return route;
    }

    public void setRoute(ArrayList<LatLng> route) {
        this.route = route;
    }

    private ArrayList<LatLng> route;

    public void setMark(int mark) {
        this.mark = mark;
    }


    private int mark;
    public Responce(){
        route = new ArrayList<>();
    }
    public void addPoint(LatLng newCoordinate){
        route.add(newCoordinate);
    }

    public boolean removePoint(){
        if(route.size() == 0){
            return false;
        }
        route.remove(route.size() - 1);
        return true;
    }
}
