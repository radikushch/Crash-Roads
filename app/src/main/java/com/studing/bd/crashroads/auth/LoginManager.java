package com.studing.bd.crashroads.auth;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class LoginManager implements ILoginManager{

    private ILoginActivity loginActivity;
    private FirebaseAuth mFireBaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    public LoginManager(ILoginActivity loginActivity){
        this.loginActivity = loginActivity;
    }



    @Override
    public void checkUserSignedIn() {
        FirebaseUser currentUser = mFireBaseAuth.getCurrentUser();
        updateUI(currentUser);
    }

    /**
        Update Ui or change activity after authentication
        @param currentUser signed in user
     */
    private void updateUI(FirebaseUser currentUser) {
    }
}
