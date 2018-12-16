package com.studing.bd.crashroads.ui.login;

import android.content.Context;
import android.content.Intent;

import com.studing.bd.crashroads.ErrorHandler;

public interface ILoginActivity extends ErrorHandler {

    String getEmail();
    String getPassword();
    void showError(String errorMessage);
    void startNewActivity(Intent intent);
    void startNewActivityForResult(Intent intent);
    Context getContext();
    String getStringResource(int resources);
}
