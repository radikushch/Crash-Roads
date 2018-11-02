package com.studing.bd.crashroads.auth.login;

import android.content.Context;
import android.content.Intent;

public interface ILoginActivity {

    String getEmail();
    String getPassword();
    void showError(String errorMessage);
    void startNewActivity(Intent intent);
    void startNewActivityForResult(Intent intent);
    Context getContext();
    String getStringResource(int resources);
}
