package com.studing.bd.crashroads.model;


import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.IgnoreExtraProperties;
import com.studing.bd.crashroads.database.remote_database.FirebaseInstant;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@IgnoreExtraProperties
public class Route implements Serializable {

    private String uid;
    private List<LatLng> points;
    private int mark;
    private long time;

    public Route(){
        points = new ArrayList<>();
        uid = FirebaseInstant.user().getUid();
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUid() {
        return uid;
    }

    public List<LatLng> getRoute() {
        return points;
    }

    public void setRoute(List<LatLng> route) {
        this.points = route;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    public int getMark() {
        return mark;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public void addPoint(LatLng newCoordinate){
        points.add(newCoordinate);
    }

    public boolean removePoint(){
        if(points.size() == 0){
            return false;
        }
        points.remove(points.size() - 1);
        return true;
    }

    @Override
    public String toString() {
        return uid +
                "\n" +
                mark +
                "\n" +
                time +
                "\n" +
                points.size();
    }
}
