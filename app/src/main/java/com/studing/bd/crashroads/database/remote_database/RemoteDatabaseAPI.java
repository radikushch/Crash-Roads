package com.studing.bd.crashroads.database.remote_database;

import com.studing.bd.crashroads.model.Route;
import com.studing.bd.crashroads.model.User;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

public class RemoteDatabaseAPI {

    public static Completable insert(User user) {
        return Completable.create(emitter -> FirebaseInstant.userReference().child(user.uid).setValue(user)
                .addOnSuccessListener(aVoid -> emitter.onComplete())
                .addOnFailureListener(emitter::onError));
    }

    public static Completable insert(Route route) {
        return Completable.create(emitter -> FirebaseInstant.routesReference()
                .push()
                .setValue(route)
                .addOnSuccessListener(aVoid -> emitter.onComplete())
                .addOnFailureListener(emitter::onError));
    }

}
