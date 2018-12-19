package com.studing.bd.crashroads.tabs.account_tab;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Log;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.studing.bd.crashroads.R;
import com.studing.bd.crashroads.Utils;
import com.studing.bd.crashroads.database.remote_database.FirebaseInstant;
import com.studing.bd.crashroads.database.remote_database.RemoteDatabaseAPI;
import com.studing.bd.crashroads.database.remote_database.RemoteStorageAPI;
import com.studing.bd.crashroads.model.CurrentUser;
import com.studing.bd.crashroads.model.User;

import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class AccountModel {
    private static final String TAG = "MyFragment";

    public interface OnUpdateCallback {
        void onUpdate(User newUSer);
        void onError(String message);
    }

    private OnUpdateCallback callback;
    private Callback photoCallback;

    public AccountModel(OnUpdateCallback callback, Callback photoCallback) {
        this.callback = callback;
        this.photoCallback = photoCallback;
    }

    public void loadPhoto(Uri uri, CircleImageView photoImageView) {
        Picasso.get()
                .load(uri)
                .placeholder(R.drawable.ic_action_face)
                .into(photoImageView, photoCallback);
    }

    public void updateUserData(User user) {
        updateIntoFirebaseDatabase(user);
    }

    private void updateIntoFirebaseDatabase(User user) {
        RemoteStorageAPI.delete()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableCompletableObserver() {
                    @Override
                    public void onComplete() {
                        uploadPhoto(user);
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError(e.getMessage());
                    }
                });

    }

    private void uploadPhoto(User user) {
        RemoteStorageAPI.upload(user, user.uid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableCompletableObserver() {
                    @Override
                    public void onComplete() {
                        getPhotoUri(user);
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError(e.getMessage());
                    }
                });
    }

    private void getPhotoUri(User user) {
        RemoteStorageAPI.getUri(user.uid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        user.imageUrl = String.valueOf(uri);
                        saveRemote(user);
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError(e.getMessage());
                    }
                });

    }

    private void saveRemote(User user) {
        RemoteDatabaseAPI.insert(user)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableCompletableObserver() {
                    @Override
                    public void onComplete() {
                        callback.onUpdate(user);
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError(e.getMessage());
                    }
                });
    }
}
