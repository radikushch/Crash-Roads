package com.studing.bd.crashroads.auth.login;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.studing.bd.crashroads.ErrorHandler;
import com.studing.bd.crashroads.Utils;
import com.studing.bd.crashroads.database.local_database.LocalDatabaseAPI;
import com.studing.bd.crashroads.database.remote_database.FirebaseInstant;
import com.studing.bd.crashroads.database.remote_database.RemoteDatabaseAPI;
import com.studing.bd.crashroads.database.remote_database.RemoteStorageAPI;
import com.studing.bd.crashroads.model.User;


import io.reactivex.Completable;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class LoginModel {

    private static String TAG = "debugDb";

    interface OnResponseCallback {
        void onSave(User user);
        void onFail(String message);
        void onResume(User user);
    }

    private OnResponseCallback callback;

    public LoginModel(OnResponseCallback callback) {
        this.callback = callback;
    }

    public void loadUser() {
        DatabaseReference ref = FirebaseInstant.userReference()
                .child(FirebaseInstant.user().getUid());
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue() != null) {
                    User user =  dataSnapshot.getValue(User.class);
                    callback.onResume(user);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                callback.onFail(databaseError.getMessage());
            }
        });
    }

    public void createUserWithEmail() {
        DatabaseReference ref = FirebaseInstant.userReference()
                .child(FirebaseInstant.user().getUid());
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue() == null) {
                    LocalDatabaseAPI.query(FirebaseInstant.user().getUid())
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new SingleObserver<User>() {
                                @Override
                                public void onSubscribe(Disposable d) {
                                }

                                @Override
                                public void onSuccess(User user) {
                                    uploadPhoto(user);
                                }

                                @Override
                                public void onError(Throwable e) {
                                    callback.onFail(e.getMessage());
                                }
                            });
                }else {
                    User user =  dataSnapshot.getValue(User.class);
                    callback.onResume(user);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                callback.onFail(databaseError.getMessage());
            }
        });
    }


    private void uploadPhoto(User user) {
        String caption = FirebaseInstant.user().getUid();
        RemoteStorageAPI.upload(user, caption)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableCompletableObserver() {
                    @Override
                    public void onComplete() {
                        getUri(user, caption);
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onFail(e.getMessage());
                    }
                });

    }

    private void getUri(User user, String caption) {
        RemoteStorageAPI.getUri(caption)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        user.imageUrl = uri.toString();
                        saveRemoteUser(user);
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onFail(e.getMessage());
                    }
                });

    }

    private void saveRemoteUser(User user) {
        RemoteDatabaseAPI.insert(user)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableCompletableObserver() {
                    @Override
                    public void onComplete() {
                        deleteLocalUser(user);
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onFail(e.getMessage());
                    }
                });
    }

    private void deleteLocalUser(User user) {
        Completable.fromAction(() -> LocalDatabaseAPI.delete(user))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableCompletableObserver() {
                    @Override
                    public void onComplete() {
                        callback.onSave(user);
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onFail(e.getMessage());
                    }
                });
    }

    public void createUserWithGoogle(final User user, final ErrorHandler loginErrorNotificator) {
        DatabaseReference ref = FirebaseDatabase.getInstance()
                .getReference()
                .child("users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue() == null) {
                    RemoteDatabaseAPI.insert(user)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new DisposableCompletableObserver() {
                                @Override
                                public void onComplete() {
                                    callback.onSave(user);
                                }

                                @Override
                                public void onError(Throwable e) {
                                    callback.onFail(e.getMessage());
                                }
                            });

                }else {
                    User user = dataSnapshot.getValue(User.class);
                    callback.onResume(user);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                loginErrorNotificator.handleError(databaseError.getMessage());
            }
        });
    }
}
