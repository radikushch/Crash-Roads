package com.studing.bd.crashroads.ui.registration;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;

import com.studing.bd.crashroads.ErrorHandler;
import com.studing.bd.crashroads.model.Gender;

public interface IRegistrationActivity extends ErrorHandler {
    String getEmail();
    String getPassword1();
    String getPassword2();
    String getName();
    String getSurname();
    String getDrivingExperience();
    String getBirthDayDate();
    void showProgressBar();
    void hideProgressBar();

    void startNewActivity(Intent intent);
    void startNewActivityForResult(Intent intent, String action);
    Context getContext();

    Bitmap getUserPhotoBitmap();

    void stop();
}
