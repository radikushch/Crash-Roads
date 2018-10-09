package com.studing.bd.crashroads.auth;

public class LoginManager implements ILoginManager {

    private ILoginActivity loginActivity;

    public LoginManager(ILoginActivity loginActivity) {
        this.loginActivity = loginActivity;
    }

    @Override
    public void validateEmail() {

    }

    @Override
    public void login() {

    }

    @Override
    public void emailSignUp() {

    }

    @Override
    public void googleSignUp() {

    }
}
