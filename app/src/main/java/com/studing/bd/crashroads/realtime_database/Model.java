package com.studing.bd.crashroads.realtime_database;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Model {
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    public Model(){
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("route");
    }
    public void saveRoute(Route route){

    }

}
