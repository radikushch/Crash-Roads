package com.studing.bd.crashroads.database.remote_database;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.studing.bd.crashroads.model.User;

import io.reactivex.Completable;

public class RemoteDatabaseAPI {

    public static Completable insert(User user) {
        return Completable.create(emitter -> FirebaseInstant.userReference().child(user.uid).setValue(user)
                .addOnSuccessListener(aVoid -> emitter.onComplete())
                .addOnFailureListener(emitter::onError));

    }
}
