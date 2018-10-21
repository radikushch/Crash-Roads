package com.studing.bd.crashroads.auth;

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
import com.studing.bd.crashroads.ErrorsContract;
import com.studing.bd.crashroads.R;

public class LoginActivity extends AppCompatActivity implements ILoginActivity {

    private AutoCompleteTextView emailTextView;
    private EditText passwordEditText;
    private Button signInButton, emailSignUpButton;
    private SignInButton googleSignInButton;

    private ILoginManager loginManager;


    @Override
    protected void onStart() {
        super.onStart();
        loginManager.checkUserSignedIn();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginManager = new LoginManager(this);
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
                //loginManager.login();
            }
        });

        emailSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginManager.emailSignUp();
            }
        });

        googleSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginManager.googleSignIn();
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
    public void showError(int errorId) {
        switch(errorId) {
            case ErrorsContract.LoginErrors.WRONG_EMAIL_ADDRESS:
                showEmailError();
                break;
            case ErrorsContract.LoginErrors.WRONG_PASSWORD:
                showPasswordError();
                break;
            case ErrorsContract.LoginErrors.USER_IS_ALREADY_EXISTS:
                showNoSuchUserError();
                break;
            case ErrorsContract.LoginErrors.NO_SUCH_USER:
                showUserIsExistsError();
                break;
        }
    }

    private void showNoSuchUserError() {
        Toast.makeText(this, "No such user", Toast.LENGTH_SHORT).show();
    }

    private void showUserIsExistsError() {
        Toast.makeText(this, "Such user is already exists", Toast.LENGTH_SHORT).show();
    }

    private void showPasswordError() {
        Toast.makeText(this, "Incorrect password", Toast.LENGTH_SHORT).show();
        passwordEditText.setError("Error");
    }

    private void showEmailError() {
        Toast.makeText(this, "Incorrect email", Toast.LENGTH_SHORT).show();
        emailTextView.setError("Error");
    }

    @Override
    public void startNewActivity(Intent intent) {
        startActivity(intent);
    }
}

