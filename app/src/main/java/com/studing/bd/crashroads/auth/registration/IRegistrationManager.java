package com.studing.bd.crashroads.auth.registration;

import android.content.Intent;

import com.studing.bd.crashroads.auth.registration.IRegistrationActivity;

public interface IRegistrationManager {
    void attachRegistrationActivity(IRegistrationActivity registrationActivity);
    void detachRegistrationActivity();
    void emailSignUp();


}
