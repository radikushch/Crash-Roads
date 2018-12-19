package com.studing.bd.crashroads.model;


import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.IgnoreExtraProperties;
import com.studing.bd.crashroads.database.remote_database.FirebaseInstant;

import java.io.Serializable;
import java.util.ArrayList;

@IgnoreExtraProperties
public class Response implements Serializable {

    private String uid;
    private ArrayList<LatLng> route;
    private int mark;
    private long time;

    public Response(){
        route = new ArrayList<>();
        uid = FirebaseInstant.user().getUid();
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUid() {
        return uid;
    }

    public ArrayList<LatLng> getRoute() {
        return route;
    }

    public void setRoute(ArrayList<LatLng> route) {
        this.route = route;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
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

    public int getMark() {
        return mark;
    }
}
