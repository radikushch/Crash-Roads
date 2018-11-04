package com.studing.bd.crashroads.auth.login;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.SignInButton;
import com.studing.bd.crashroads.R;


public class LoginActivity extends AppCompatActivity implements ILoginActivity {

    private AutoCompleteTextView emailTextView;
    private EditText passwordEditText;
    private Button signInButton;
    private SignInButton googleSignInButton;
    private TextView emailSignUpButton, guestSignInButton;

    private ILoginManager loginManager;

    private static final int RC_SIGN_IN = 1;

    @Override
    protected void onStart() {
        super.onStart();
        loginManager.checkUserSignedIn();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        loginManager.detachLoginActivity();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginManager = new LoginManager();
        loginManager.attachLoginActivity(this);
        initViews();
        listenersSettings();
    }

    private void initViews() {
        emailTextView = findViewById(R.id.email);
        passwordEditText = findViewById(R.id.password);
        signInButton = findViewById(R.id.email_sign_in_button);
        googleSignInButton = findViewById(R.id.google_sign_in_button);
        googleSignInButton.setSize(SignInButton.SIZE_STANDARD);
        emailSignUpButton = findViewById(R.id.sign_up_button);
        guestSignInButton = findViewById(R.id.guest_button);
    }

    private void listenersSettings() {
        emailTextView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_NULL) {
                    loginManager.validateEmail();
                }
                return true;
            }
        });

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginManager.login();
            }
        });

        emailSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginManager.signUp();
            }
        });

        googleSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginManager.chooseGoogleUser();
            }
        });

        guestSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginManager.anonymousLogin();
            }
        });
    }

    @Override
    public String getEmail() {
        return String.valueOf(emailTextView.getText());
    }

    @Override
    public String getPassword() {
        return String.valueOf(passwordEditText.getText());
    }

    @Override
    public void showError(String errorMessage) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
    }

    @Override
    public void startNewActivity(Intent intent) {
        startActivity(intent);
        finish();
    }

    @Override
    public void startNewActivityForResult(Intent intent) {
        startActivityForResult(intent, RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RC_SIGN_IN) {
           loginManager.googleLogin(data);
        }
    }

    @Override
    public Context getContext() {
        return getApplicationContext();
    }

    @Override
    public String getStringResource(int resources) {
        return getString(resources);
    }
}

