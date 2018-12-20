package com.studing.bd.crashroads;


import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.android.gms.maps.model.LatLng;
import com.studing.bd.crashroads.model.Route;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class JSONParser {


    public static Route parse(Object value) {
        Route route = new Route();
        String gson = new Gson().toJson(value);
        JsonParser parser = new JsonParser();
        JsonObject object = (JsonObject) parser.parse(gson);
        String mark = String.valueOf(object.get("mark"));
        String time = String.valueOf(object.get("time"));
        String uid = String.valueOf(object.get("uid"));
        JsonArray jsonPoints = object.getAsJsonArray("route");
        String latitude;
        String longtitude;
        JsonObject current;
        List<LatLng> points = new ArrayList<>();
        for (int i = 0; i < jsonPoints.size(); i++) {
            current = jsonPoints.get(i).getAsJsonObject();
            latitude = String.valueOf(current.get("latitude"));
            longtitude = String.valueOf(current.get("longitude"));
            points.add(new LatLng(Double.parseDouble(latitude), Double.parseDouble(longtitude)));
        }
        route.setRoute(points);
        route.setMark(Integer.parseInt(mark));
        route.setTime(Long.parseLong(time));
        route.setUid(uid);
        return route;
    }
}
