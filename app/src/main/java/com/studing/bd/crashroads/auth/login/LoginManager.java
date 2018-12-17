package com.studing.bd.crashroads.auth.login;


import android.content.Intent;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.studing.bd.crashroads.MainActivity;
import com.studing.bd.crashroads.R;
import com.studing.bd.crashroads.Utils;
import com.studing.bd.crashroads.database.remote_database.FirebaseInstant;
import com.studing.bd.crashroads.ui.login.ILoginActivity;
import com.studing.bd.crashroads.ui.login.LoginActivity;
import com.studing.bd.crashroads.ui.registration.RegistrationActivity;
import com.studing.bd.crashroads.model.User;


public class LoginManager implements ILoginManager, LoginModel.OnResponseCallback {

    private ILoginActivity loginActivity;
    private FirebaseAuth mFireBaseAuth;
    private LoginModel loginModel;
    private static final String EMAIL_VALIDATION_ERROR = "Invalid email format";
    private final static String TAG = "login Error";


    public LoginManager(ILoginActivity loginActivity){
        this.loginActivity = loginActivity;
        mFireBaseAuth = FirebaseAuth.getInstance();
        loginModel = new LoginModel(this);
    }

    @Override
    public void checkUserSignedIn() {
        FirebaseUser currentUser = mFireBaseAuth.getCurrentUser();
        if(currentUser != null) {
            updateUI(currentUser);
        }
    }

    @Override
    public void login() {
        String email = loginActivity.getEmail();
        String password = loginActivity.getPassword();
        loginActivity.showProgressBar();
        fireBaseLogin(email, password);
    }

    private void fireBaseLogin(String email, String password) {
        mFireBaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser currentUser = mFireBaseAuth.getCurrentUser();
                        if(currentUser != null && !currentUser.isEmailVerified()) {
                            loginActivity.showError("Verify email");
                            mFireBaseAuth.signOut();
                        }
                        else {
                            loginModel.createUserWithEmail();
                        }
                    }
                })
                .addOnFailureListener(e -> loginActivity.showError(e.getMessage()));
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
            if (account != null)
                fireBaseGoogleLogin(account);
        } catch (ApiException e) {
           loginActivity.showError(e.getMessage());
        }
    }

    private void fireBaseGoogleLogin(GoogleSignInAccount account) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mFireBaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()) {
                        FirebaseUser firebaseUser = mFireBaseAuth.getCurrentUser();
                        if(firebaseUser != null) {
                            User user = createUser(mFireBaseAuth.getCurrentUser());
                            loginModel.createUserWithGoogle(user, new LoginActivity());
                        }

                    }
                }).addOnFailureListener(e -> loginActivity.showError(e.getMessage()));
    }

    private User createUser(FirebaseUser currentUser) {
        return User.builder()
                .uid(currentUser.getUid())
                .email(currentUser.getEmail())
                .name(currentUser.getDisplayName())
                .imageUrl(String.valueOf(currentUser.getPhotoUrl()))
                .build();
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
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()) {
                        FirebaseUser user = mFireBaseAuth.getCurrentUser();
                        updateUI(user);
                    }
                }).addOnFailureListener(e -> loginActivity.showError(e.getMessage()));
    }



    @Override
    public void validateEmail() {
        String email = loginActivity.getEmail();
        if(!Utils.isEmailCorrect(email))
            loginActivity.showError(EMAIL_VALIDATION_ERROR);
    }

    @Override
    public void onSave() {
        loginActivity.hideProgressBar();
        updateUI(FirebaseInstant.user());
    }

    @Override
    public void onFail(String message) {
        loginActivity.hideProgressBar();
        loginActivity.handleError(message);
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
