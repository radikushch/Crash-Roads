package com.studing.bd.crashroads.ui.account_tab;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;

import com.studing.bd.crashroads.ErrorHandler;
import com.studing.bd.crashroads.model.User;

import de.hdodenhof.circleimageview.CircleImageView;

public interface IAccountFragment extends ErrorHandler {

    void setUserInfo(User user);
    String getName();
    String getAge();
    String getEmail();
    String getLocation();
    String getExp();
    Bitmap getUserPhotoBitmap();
    void handleError(String message);
    CircleImageView getImageContainer();
    void startNewActivityForResult(Intent intent, String action);

}
