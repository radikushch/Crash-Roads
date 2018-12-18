package com.studing.bd.crashroads.account_tab;

import android.net.Uri;
import android.util.Log;

import com.squareup.picasso.Picasso;
import com.studing.bd.crashroads.database.remote_database.RemoteDatabaseAPI;
import com.studing.bd.crashroads.model.User;

import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.schedulers.Schedulers;

public class AccountModel {
    private static final String TAG = "MyFragment";


    public interface OnUpdateCallback {
        void onUpdate(User newUSer);
        void onError(String message);
    }

    private OnUpdateCallback callback;

    public AccountModel(OnUpdateCallback callback) {
        this.callback = callback;
    }

    public void loadPhoto(Uri uri, CircleImageView photoImageView) {
        Picasso.get().load(uri).into(photoImageView);
    }

    public void updateUserData(User user) {
        updateIntoFirebaseDatabase(user);

    }

    private void updateIntoFirebaseDatabase(User user) {
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
