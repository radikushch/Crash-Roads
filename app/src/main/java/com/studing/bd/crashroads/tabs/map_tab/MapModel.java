package com.studing.bd.crashroads.tabs.map_tab;

import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.studing.bd.crashroads.JSONParser;
import com.studing.bd.crashroads.database.remote_database.FirebaseInstant;
import com.studing.bd.crashroads.database.remote_database.RemoteDatabaseAPI;
import com.studing.bd.crashroads.model.Route;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.function.Consumer;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.schedulers.Schedulers;

public class MapModel {

    private static final String TAG = "datasnapshot";

    interface OnSaveRouteCallback {
        void onRouteLoad(Route route);
        void onError(String message);
        void onRoadSave();
    }

    private OnSaveRouteCallback callback;

    public MapModel(OnSaveRouteCallback callback){
        this.callback = callback;
    }
    
    public void saveRoute(Route route){
        RemoteDatabaseAPI.insert(route)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableCompletableObserver() {
                    @Override
                    public void onComplete() {
                        callback.onRoadSave();
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError(e.getMessage());
                    }
                });
    }

    public void loadAllRoutes() {
        FirebaseInstant.routesReference().addListenerForSingleValueEvent(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    callback.onRouteLoad(JSONParser.parse(dataSnapshot1.getValue()));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                callback.onError(databaseError.getMessage());
            }
        });

        FirebaseInstant.routesReference().addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                callback.onRouteLoad(JSONParser.parse(dataSnapshot.getValue()));
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                callback.onRouteLoad(JSONParser.parse(dataSnapshot.getValue()));
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
