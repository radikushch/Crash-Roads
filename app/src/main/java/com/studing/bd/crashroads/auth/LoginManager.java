package com.studing.bd.crashroads.auth;


import android.content.Intent;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.studing.bd.crashroads.ErrorsContract;
import com.studing.bd.crashroads.R;

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
    public void fireBaseAuthWithEmail() {
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
    public void fireBaseLogin() {
        String email = loginActivity.getEmail();
        String password = loginActivity.getPassword();
        mFireBaseAuth.signInWithEmailAndPassword(email, password)
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
    }

    @Override
    public void openGoogleSignInForm() {
        String defaultWebClientID = loginActivity.getStringResource(R.string.default_web_client_id);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(defaultWebClientID)
                .requestEmail()
                .build();
        GoogleSignInClient signInClient = GoogleSignIn.getClient(loginActivity.getContext(), gso);
        Intent signInIntent = signInClient.getSignInIntent();
        loginActivity.startNewActivityForResult(signInIntent);
    }

    @Override
    public void fireBaseAuthWithGoogle(Intent data) {
        Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
        try{
            GoogleSignInAccount account = task.getResult(ApiException.class);
            loginGoogleAccount(account);
        } catch (ApiException e) {
            e.printStackTrace();
        }
    }

    private void loginGoogleAccount(GoogleSignInAccount account) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mFireBaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            FirebaseUser user = mFireBaseAuth.getCurrentUser();
                            updateUI(user);
                        }else{
                            loginActivity.showError(ErrorsContract.LoginErrors.NO_SUCH_USER);
                            updateUI(null);
                        }
                    }
                });
    }


    /**
        Update Ui or change activity after authentication
        @param currentUser signed in user
     */
    private void updateUI(FirebaseUser currentUser) {
        Toast.makeText(loginActivity.getContext(), "user signed in", Toast.LENGTH_SHORT).show();
    }
}
