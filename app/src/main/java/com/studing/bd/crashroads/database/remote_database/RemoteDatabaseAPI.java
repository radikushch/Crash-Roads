package com.studing.bd.crashroads.database.remote_database;

import com.studing.bd.crashroads.model.Response;
import com.studing.bd.crashroads.model.User;

import io.reactivex.Completable;

public class RemoteDatabaseAPI {

    public static Completable insert(User user) {
        return Completable.create(emitter -> FirebaseInstant.userReference().child(user.uid).setValue(user)
                .addOnSuccessListener(aVoid -> emitter.onComplete())
                .addOnFailureListener(emitter::onError));
    }

    public static Completable insert(Response response) {
        return Completable.create(emitter -> FirebaseInstant.routesReference()
                .push()
                .setValue(response)
                .addOnSuccessListener(aVoid -> emitter.onComplete())
                .addOnFailureListener(emitter::onError));
    }
}
