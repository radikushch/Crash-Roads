package com.studing.bd.crashroads.model;

public class WayPoint {
    private double latitude;
    private double longtitude;


    public WayPoint(double latitude, double longtitude){
        this.latitude = latitude;
        this.longtitude = longtitude;
    }

    public double getLongtitude() {
        return longtitude;
    }

    public void setLongtitude(double longtitude) {
        this.longtitude = longtitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
}
