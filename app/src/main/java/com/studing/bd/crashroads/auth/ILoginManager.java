package com.studing.bd.crashroads.auth;

import android.content.Intent;

public interface ILoginManager {
    void checkUserSignedIn();
    void validateEmail();
    void fireBaseAuthWithEmail();
    void fireBaseLogin();
    void openGoogleSignInForm();
    void fireBaseAuthWithGoogle(Intent data);
}
