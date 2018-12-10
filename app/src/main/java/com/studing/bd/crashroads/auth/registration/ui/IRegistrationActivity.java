package com.studing.bd.crashroads.auth.registration.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;

import com.studing.bd.crashroads.model.Gender;

public interface IRegistrationActivity {
    String getEmail();
    String getPassword1();
    String getPassword2();
    String getName();
    Gender getGender();
    String getDrivingExperience();
    String getBirthDayDate();

    void showError(String message);
    void startNewActivity(Intent intent);
    void startNewActivityForResult(Intent intent, String action);
    Context getContext();

    Bitmap getUserPhotoBitmap();

    void stop();
}
