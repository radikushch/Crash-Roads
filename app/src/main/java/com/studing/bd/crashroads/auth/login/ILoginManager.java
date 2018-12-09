package com.studing.bd.crashroads.auth.login;

import android.content.Intent;

import com.studing.bd.crashroads.auth.login.ui.ILoginActivity;

public interface ILoginManager {
    void checkUserSignedIn();
    void validateEmail();
    void login();
    void chooseGoogleUser();
    void googleLogin(Intent data);
    void signUp();
    void anonymousLogin();
}
