package com.studing.bd.crashroads.model;

import java.util.ArrayList;
import java.util.List;

public class Route {
    private List<WayPoint> way;
    private String userId;

    public Route() {
        way = new ArrayList<>();
        userId = CurrentUser.get().uid;
    }

    public List<WayPoint> getWay() {
        return way;
    }

    public void addWaypoint(WayPoint point){
        way.add(point);
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
