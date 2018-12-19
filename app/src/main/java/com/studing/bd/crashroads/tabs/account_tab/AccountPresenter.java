package com.studing.bd.crashroads.tabs.account_tab;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;

import com.studing.bd.crashroads.Utils;
import com.studing.bd.crashroads.model.CurrentUser;
import com.studing.bd.crashroads.model.User;
import com.studing.bd.crashroads.ui.account_tab.IAccountFragment;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

public class AccountPresenter implements IAccountPresenter, AccountModel.OnUpdateCallback {

    private IAccountFragment accountFragment;
    private Bundle viewState;
    private static final String SELECT_PHOTO_ACTION = "Select Picture";
    private AccountModel accountModel;

    public AccountPresenter(IAccountFragment accountFragment) {
        this.accountFragment = accountFragment;
        accountModel = new AccountModel(this);
        viewState = new Bundle();
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
    public void loadUserData() {
        User user = CurrentUser.get();
        accountFragment.setUserInfo(user);
        if(user.imageByte == null)
            accountModel.loadPhoto(Uri.parse(user.imageUrl), accountFragment.getImageContainer());
        else accountFragment.getImageContainer().setImageBitmap(Utils.arrayToBitmap(user.imageByte));
    }

    @Override
    public void updateUserInfo() {
        User user = CurrentUser.get();
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
        accountFragment.setUserInfo(user);
        user.imageByte = Utils.bitmapToArray(accountFragment.getUserPhotoBitmap());
        accountModel.updateUserData(user);
    }

    @Override
    public void loadProfilePhoto() {
        openGallery();
    }

    private void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        accountFragment.startNewActivityForResult(intent, SELECT_PHOTO_ACTION);
    }

    @Override
    public void onUpdate(User newUser) {
        CurrentUser.set(newUser);
    }

    @Override
    public void onError(String message) {
        accountFragment.handleError(message);
    }
}
