package com.studing.bd.crashroads.ui.login;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.SignInButton;
import com.studing.bd.crashroads.ErrorHandler;
import com.studing.bd.crashroads.R;
import com.studing.bd.crashroads.auth.login.ILoginManager;
import com.studing.bd.crashroads.auth.login.LoginManager;
import com.studing.bd.crashroads.database.local_database.LocalDatabaseAPI;

import butterknife.BindView;
import io.reactivex.Completable;
import io.reactivex.schedulers.Schedulers;


public class LoginActivity extends AppCompatActivity implements ILoginActivity,
        ErrorHandler {

    @BindView(R.id.email) AutoCompleteTextView emailTextView;
    @BindView(R.id.password) EditText passwordEditText;
    @BindView(R.id.email_sign_in_button) Button signInButton;
    @BindView(R.id.google_sign_in_button) SignInButton googleSignInButton;
    @BindView(R.id.sign_up_button) TextView emailSignUpButton;
    @BindView(R.id.guest_button) TextView guestSignInButton;
    @BindView(R.id.login_container) ConstraintLayout layoutContainer;
    @BindView(R.id.login_loading) ProgressBar loadProgressBar;

    private ILoginManager loginManager;

    private static final int RC_SIGN_IN = 1;

    @Override
    protected void onResume() {
        super.onResume();
        loginManager.checkUserSignedIn();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginManager = new LoginManager(this);
        googleSignInButton.setSize(SignInButton.SIZE_STANDARD);
        listenersSettings();
    }

    private void listenersSettings() {
        emailTextView.setOnEditorActionListener((v, actionId, event) -> {
            if(actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_NULL) {
                loginManager.validateEmail();
            }
            return true;
        });

        signInButton.setOnClickListener(v -> loginManager.login());
        emailSignUpButton.setOnClickListener(v -> loginManager.signUp());
        googleSignInButton.setOnClickListener(v -> loginManager.chooseGoogleUser());
        guestSignInButton.setOnClickListener(v -> loginManager.anonymousLogin());
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

    @Override
    public void showProgressBar() {
        layoutContainer.setVisibility(View.GONE);
        loadProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        layoutContainer.setVisibility(View.VISIBLE);
        loadProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void handleError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}

