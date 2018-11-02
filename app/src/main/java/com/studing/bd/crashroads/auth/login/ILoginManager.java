package com.studing.bd.crashroads.auth.login;

import android.content.Intent;

import com.studing.bd.crashroads.auth.login.ILoginActivity;

public interface ILoginManager {
    void attachLoginActivity(ILoginActivity loginActivity);
    void detachLoginActivity();
    void checkUserSignedIn();
    void validateEmail();
    void login();
    void chooseGoogleUser();
    void googleLogin(Intent data);
    void signUp();
}
