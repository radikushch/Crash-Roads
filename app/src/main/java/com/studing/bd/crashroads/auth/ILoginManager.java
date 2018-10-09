package com.studing.bd.crashroads.auth;

public interface ILoginManager {

    void validateEmail();
    void login();
    void emailSignUp();
    void googleSignUp();
}
