package com.studing.bd.crashroads.auth.registration;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.widget.ImageView;

public interface IRegistrationActivity {
    String getEmail();
    String getPassword1();
    String getPassword2();
    String getName();
    void showError(String message);
    void startNewActivity(Intent intent);
    void startNewActivityForResult(Intent intent, String action);
    Context getContext();

    Bitmap getUserPhotoBitmap();
}
