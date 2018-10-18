package com.studing.bd.crashroads.auth;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.studing.bd.crashroads.R;

public class LoginActivity extends AppCompatActivity{

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
        setContentView(R.layout.activity_main);
        loginManager = new LoginManager(this);
        loginManager.init();

    }
}

