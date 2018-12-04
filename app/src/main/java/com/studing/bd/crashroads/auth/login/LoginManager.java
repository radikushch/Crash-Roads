package com.studing.bd.crashroads.auth.login;


import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.FirebaseDatabase;
import com.studing.bd.crashroads.MainActivity;
import com.studing.bd.crashroads.R;
import com.studing.bd.crashroads.User;
import com.studing.bd.crashroads.Utils;
import com.studing.bd.crashroads.auth.registration.RegistrationActivity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class LoginManager implements ILoginManager {

    private ILoginActivity loginActivity;
    private FirebaseAuth mFireBaseAuth;
    private LoginModel loginModel;
    private static final String EMAIL_VALIDATION_ERROR = "Invalid email format";

    public LoginManager(){
        mFireBaseAuth = FirebaseAuth.getInstance();
        loginModel = new LoginModel();
    }

    @Override
    public void attachLoginActivity(ILoginActivity loginActivity) {
        this.loginActivity = loginActivity;
    }

    @Override
    public void detachLoginActivity() {
        loginActivity = null;
    }

    @Override
    public void checkUserSignedIn() {
        FirebaseUser currentUser = mFireBaseAuth.getCurrentUser();
        if(currentUser != null) {
            currentUser.reload();
            if(currentUser.isEmailVerified()) {
                updateUI(currentUser);
            }else {
                loginActivity.showError("Verify your email address");
            }
        }
    }

    @Override
    public void validateEmail() {
        String email = loginActivity.getEmail();
        if(!Utils.isEmailCorrect(email))
            loginActivity.showError(EMAIL_VALIDATION_ERROR);
    }

    @Override
    public void login() {
        String email = loginActivity.getEmail();
        String password = loginActivity.getPassword();
        fireBaseLogin(email, password);
    }

    private void fireBaseLogin(String email, String password) {
        mFireBaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mFireBaseAuth.getCurrentUser();
                            if (user != null) {
                                if(!user.isEmailVerified()) {
                                    loginActivity.showError("Verify your email address");
                                }else {
                                    updateUI(user);
                                }
                            }else {
                                loginActivity.showError("No such user");
                            }

                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        loginActivity.showError(e.getMessage());
                    }
                });
    }


    /**
     * Opening google account dialog to choose account for login
     */
    @Override
    public void chooseGoogleUser() {
        String defaultWebClientID = loginActivity.getStringResource(R.string.default_web_client_id);
        openGoogleSignInForm(defaultWebClientID);
    }

    private void openGoogleSignInForm(String defaultWebClientID) {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(defaultWebClientID)
                .requestEmail()
                .build();
        GoogleSignInClient signInClient = GoogleSignIn.getClient(loginActivity.getContext(), gso);
        Intent signInIntent = signInClient.getSignInIntent();
        loginActivity.startNewActivityForResult(signInIntent);
    }


    /**
     * login google account
     * @param data intent with account class
     */
    @Override
    public void googleLogin(Intent data) {
        Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
        try{
            GoogleSignInAccount account = task.getResult(ApiException.class);
            fireBaseGoogleLogin(account);
        } catch (ApiException e) {
           loginActivity.showError(e.getMessage());
        }
    }

    private void fireBaseGoogleLogin(GoogleSignInAccount account) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mFireBaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            FirebaseUser firebaseUser = mFireBaseAuth.getCurrentUser();
                            loginModel.createUser(firebaseUser);
                            updateUI(firebaseUser);
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        loginActivity.showError(e.getMessage());
                    }
                });
    }


    /**
     * Open registration form
     */
    @Override
    public void signUp() {
        Intent intent = new Intent(loginActivity.getContext(), RegistrationActivity.class);
        loginActivity.startNewActivity(intent);
    }

    /**
     * Anonymous login
     */
    @Override
    public void anonymousLogin() {
        mFireBaseAuth.signInAnonymously()
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            FirebaseUser user = mFireBaseAuth.getCurrentUser();
                            updateUI(user);
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        loginActivity.showError(e.getMessage());
                    }
                });
    }


    /**
        Update Ui or change activity after authentication
        @param currentUser signed in user
     */
    private void updateUI(FirebaseUser currentUser) {
        if(currentUser != null) {
            if(currentUser.isAnonymous()) {

            }else {
                Intent intent = new Intent(loginActivity.getContext(), MainActivity.class);
                loginActivity.startNewActivity(intent);
            }
        }
    }
}
