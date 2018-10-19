package com.studing.bd.crashroads.auth;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.studing.bd.crashroads.R;
import com.studing.bd.crashroads.realtime_database.MapActivity;
import com.studing.bd.crashroads.realtime_database.MapFragment;

import java.util.Arrays;
import java.util.List;

public class LoginActivity extends AppCompatActivity implements ILoginActivity{
    private static final int RC_SIGN_IN = 123;
    private AutoCompleteTextView emailTextView;
    private EditText passwordEditText;
    private Button signInButton, googleSignInButton, emailSignUpButton;

    private LoginManager loginManager;
    private TextView mTextMessage;


    @Override
    protected void onPause() {
        super.onPause();
        loginManager.pause();

    }

    @Override
    protected void onResume() {
        super.onResume();
        loginManager.resume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginManager = new LoginManager(this);
        loginManager.init();
    }

    @Override
    public void startLogIn() {
        Log.d("flag", "inside startLogIn()");
        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build(),
                new AuthUI.IdpConfig.GoogleBuilder().build());
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .build(),
                RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RC_SIGN_IN){
            IdpResponse response = IdpResponse.fromResultIntent(data);
            if(resultCode == RESULT_OK){
                Log.d("flag", "inside onActivityResult()");
                Intent intent = new Intent(this, MapActivity.class);
                startActivity(intent);
            }
        }else{
            Log.d("flag", "result code doesn`t match RC_SIGN_IN");
        }
    }
}

