package com.studing.bd.crashroads.realtime_database;

import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Model {
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    public Model(){
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("routes");
    }
    public void saveRoute(Responce responce){
        databaseReference.push().setValue(responce);
        Log.d("flag", "info saved  tinto database");
    }

    public void fetchRoute(){

    }

}
