package com.studing.bd.crashroads.tabs.account_tab;

import android.os.Bundle;

import de.hdodenhof.circleimageview.CircleImageView;

public interface IAccountPresenter {

    void saveViewState();
    Bundle getViewState();
    void loadUserData();
    void updateUserInfo();

    void loadProfilePhoto();
}
