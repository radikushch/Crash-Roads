package com.studing.bd.crashroads.ui.account_tab;

import android.graphics.Bitmap;

import com.studing.bd.crashroads.ErrorHandler;
import com.studing.bd.crashroads.model.User;

public interface IAccountFragment extends ErrorHandler {

    void setUserInfo(User user);
    String getName();
    String getAge();
    String getEmail();
    String getLocation();
    String getExp();
    Bitmap getUserPhotoBitmap();

}
