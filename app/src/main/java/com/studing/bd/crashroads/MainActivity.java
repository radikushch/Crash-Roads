package com.studing.bd.crashroads;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
//import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {


    //private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private TextView mTextMessage;


    @Override
    protected void onPause() {
        super.onPause();
        if(mAuthStateListener != null){
            mFirebaseAuth.removeAuthStateListener(mAuthStateListener);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //mFirebaseDatabase = FirebaseDatabase.getInstance();
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
                    /*startActivityForResult(AuthUI.getInstance().
                                            createSignInIntentBuilder().
                                            setIsSmartLockEnabled(false).
                                            setProviders(
                                                    AuthUI.EMAIL_PROVIDER,
                                                    AuthUI.GOOGLE_PROVIDER).
                                            build(),
                                    RC_SIGN_IN);*/
                }
            }
        };
    }

}
