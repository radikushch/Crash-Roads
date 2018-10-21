package com.studing.bd.crashroads.auth;

public interface ILoginManager {
    void checkUserSignedIn();
    void validateEmail();
    void emailSignUp();
    void emailSignIn();
    void googleSignIn();
}
