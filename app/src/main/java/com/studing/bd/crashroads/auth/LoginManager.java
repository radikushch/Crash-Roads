package com.studing.bd.crashroads.auth;


import android.app.Activity;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.studing.bd.crashroads.ErrorsContract;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class LoginManager implements ILoginManager{

    private static final String TAG = LoginManager.class.getName();
    private ILoginActivity loginActivity;
    private FirebaseAuth mFireBaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    public LoginManager(ILoginActivity loginActivity){
        this.loginActivity = loginActivity;
        mFireBaseAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void checkUserSignedIn() {
        FirebaseUser currentUser = mFireBaseAuth.getCurrentUser();
        updateUI(currentUser);
    }

    @Override
    public void validateEmail() {
        String email = loginActivity.getEmail();
        if(!emailIsCorrect(email)){
            loginActivity.showError(ErrorsContract.LoginErrors.WRONG_EMAIL_ADDRESS);
        }
    }

    private boolean emailIsCorrect(String email) {
        Pattern validEmailAddressRegex =
                Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = validEmailAddressRegex.matcher(email);
        return matcher.matches();
    }

    @Override
    public void emailSignUp() {
        String email = loginActivity.getEmail();
        String password = loginActivity.getPassword();
        if(emailIsCorrect(email)) {
            mFireBaseAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                FirebaseUser user = mFireBaseAuth.getCurrentUser();
                                updateUI(user);
                            } else {
                                updateUI(null);
                            }
                        }
                    });
        }else {
            loginActivity.showError(ErrorsContract.LoginErrors.WRONG_EMAIL_ADDRESS);
        }
    }

    @Override
    public void emailSignIn() {
        String email = loginActivity.getEmail();
        String password = loginActivity.getPassword();

    }


    /**
        Update Ui or change activity after authentication
        @param currentUser signed in user
     */
    private void updateUI(FirebaseUser currentUser) {
    }
}
