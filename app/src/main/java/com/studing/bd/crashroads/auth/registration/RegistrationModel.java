package com.studing.bd.crashroads.auth.registration;

import com.google.firebase.auth.FirebaseUser;
import com.studing.bd.crashroads.database.local_database.LocalDatabaseAPI;
import com.studing.bd.crashroads.model.User;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.schedulers.Schedulers;

public class RegistrationModel {
    interface OnResponseCallback {
        void onSave();
        void onFail(String message);
    }

    private OnResponseCallback callback;

    public RegistrationModel(OnResponseCallback callback) {
        this.callback = callback;
    }

    public void saveUser(User user) {
        Completable.fromAction(() -> LocalDatabaseAPI.insert(user))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableCompletableObserver() {
                    @Override
                    public void onComplete() {
                        callback.onSave();
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onFail(e.getMessage());
                    }
                });
    }
}
