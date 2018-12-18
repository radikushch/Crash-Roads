package com.studing.bd.crashroads.account_tab;

import android.net.Uri;
import android.os.Bundle;

import com.studing.bd.crashroads.Utils;
import com.studing.bd.crashroads.database.remote_database.FirebaseInstant;
import com.studing.bd.crashroads.model.User;
import com.studing.bd.crashroads.ui.account_tab.IAccountFragment;

public class AccountPresenter implements IAccountPresenter, AccountModel.OnUpdateCallback {

    private IAccountFragment accountFragment;
    private Bundle viewState;
    private User user;
    private AccountModel accountModel;

    public AccountPresenter(IAccountFragment accountFragment, User user) {
        this.accountFragment = accountFragment;
        accountModel = new AccountModel(this);
        viewState = new Bundle();
        this.user = user;
        this.user.uid = FirebaseInstant.user().getUid();
    }

    @Override
    public void saveViewState() {
        viewState.putCharSequence("name", accountFragment.getName());
        viewState.putCharSequence("age", accountFragment.getAge());
        viewState.putCharSequence("location", accountFragment.getLocation());
        viewState.putCharSequence("exp", accountFragment.getExp());
        viewState.putByteArray("photo", Utils.bitmapToArray(accountFragment.getUserPhotoBitmap()));
    }

    @Override
    public Bundle getViewState() {
        return viewState;
    }

    @Override
    public void setUserBirthDate(String date) {
        user.birthdayDate = date;
    }

    @Override
    public void loadUserData() {
        accountFragment.setUserInfo(user);
        accountModel.loadPhoto(Uri.parse(user.imageUrl), accountFragment.getImageContainer());
    }

    @Override
    public void updateUserInfo() {
        String fullName = accountFragment.getName();
        if(fullName.length() > 0) {
            String[] nameParts = fullName.split(" ");
            user.name = nameParts[0];
            StringBuilder surnameTmp = new StringBuilder();
            for (int i = 1; i < nameParts.length; i++) {
                surnameTmp.append(nameParts[i]);
            }
            user.surname = surnameTmp.toString();
        }
        user.country = accountFragment.getLocation();
        user.drivingExperience = accountFragment.getExp();
        accountModel.updateUserData(user);
    }

    @Override
    public void onUpdate(User newUser) {
        user = newUser;
    }

    @Override
    public void onError(String message) {
        accountFragment.handleError(message);
    }
}
