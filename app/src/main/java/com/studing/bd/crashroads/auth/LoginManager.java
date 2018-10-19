package com.studing.bd.crashroads.auth;


import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTransaction;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.studing.bd.crashroads.R;
import com.studing.bd.crashroads.realtime_database.MapActivity;
import com.studing.bd.crashroads.realtime_database.MapFragment;

import java.util.Arrays;
import java.util.List;



public class LoginManager implements ILoginManager{
    private LoginActivity loginActivity;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private FragmentTransaction fragmentTransaction;
    private MapFragment mapFragment;

    public LoginManager(LoginActivity loginActivity){
        this.loginActivity = loginActivity;
        mapFragment = new MapFragment();
    }

    public void init(){
        mFirebaseAuth = FirebaseAuth.getInstance();
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user != null){
                    //TODO: attach databaseListener
                }
                else{
                    //TODO: detach databaseListener
                    loginActivity.startLogIn();
                }
            }
        };
    }

    @Override
    public void pause() {
        if(mAuthStateListener != null){
            mFirebaseAuth.removeAuthStateListener(mAuthStateListener);
        }
    }

    @Override
    public void resume() {
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }

}
