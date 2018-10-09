package com.studing.bd.crashroads.auth;

import android.content.Intent;

public interface ILoginActivity {

    String getEmail();
    String getPassword();
    void showError(int errorId);
    void startNewActivity(Intent intent);

}
