package com.studing.bd.crashroads.auth;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.KeyEvent;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.studing.bd.crashroads.R;

public class LoginActivity extends AppCompatActivity implements ILoginActivity{

    private AutoCompleteTextView emailTextView;
    private EditText passwordEditText;
    private Button signInButton, googleSignInButton, emailSignUpButton;

    private LoginManager loginManager;
    private TextView mTextMessage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginManager = new LoginManager(this);
        initViews();
    }

    private void initViews() {
        emailTextView = findViewById(R.id.email);
        emailTextView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == )
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        loginManager.checkUserSignedIn();
    }

    @Override
    public String getEmail() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public void showError(int errorId) {

    }

    @Override
    public void startNewActivity(Intent intent) {
        startActivity(intent);
    }
}

