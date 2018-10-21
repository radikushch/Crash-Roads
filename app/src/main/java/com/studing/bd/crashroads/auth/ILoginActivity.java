package com.studing.bd.crashroads.auth;

import android.content.Intent;

import com.google.android.gms.tasks.OnCompleteListener;

public interface ILoginActivity {

    String getEmail();
    String getPassword();
    void showError(int errorId);
    void startNewActivity(Intent intent);

}
