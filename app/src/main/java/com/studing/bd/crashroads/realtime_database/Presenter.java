package com.studing.bd.crashroads.realtime_database;


import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import com.google.firebase.auth.FirebaseAuth;
import com.studing.bd.crashroads.R;

import java.util.Arrays;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class Presenter {
    private MapActivity mapActivity;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private FragmentTransaction fragmentTransaction;
    private Fragment mapFragment;
    public Presenter(MapActivity activity){
        this.mapActivity = activity;
        mapFragment = new MapFragment();
    }

    public void showInitialFragment(){
        fragmentTransaction = mapActivity.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, mapFragment);
        fragmentTransaction.commit();
    }

    public boolean createMapFragment(int itemId){
        fragmentTransaction = mapActivity.getSupportFragmentManager().beginTransaction();
        Fragment selectedFragment = null;
        switch (itemId){
            case R.id.navigation_map:
                selectedFragment = mapFragment;
                break;
        }
        fragmentTransaction.replace(R.id.fragment_container, selectedFragment);
        fragmentTransaction.commit();
        return true;
    }

}
