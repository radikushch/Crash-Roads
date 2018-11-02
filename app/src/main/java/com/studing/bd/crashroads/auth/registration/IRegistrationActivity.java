package com.studing.bd.crashroads.auth.registration;

import android.content.Context;
import android.content.Intent;

public interface IRegistrationActivity {
    String getEmail();
    String getPassword1();
    String getPassword2();
    String getName();
    void showError(String message);
    void startNewActivity(Intent intent);
    Context getContext();
}
