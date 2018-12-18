package com.studing.bd.crashroads.database.remote_database;

import android.net.Uri;

import com.google.android.gms.tasks.OnSuccessListener;
import com.studing.bd.crashroads.model.User;

import io.reactivex.Completable;
import io.reactivex.Single;

public class RemoteStorageAPI {

    private static String TAG = "debugDb";

    public static Completable upload(User user, String caption) {
        return Completable.create(emitter -> FirebaseInstant
                .photoReference()
                .child(caption + ".jpg")
                .putBytes(user.imageByte)
                .addOnSuccessListener(taskSnapshot -> emitter.onComplete())
                .addOnFailureListener(emitter::onError));
    }

    public static Single<Uri> getUri(String caption) {
        return Single.create(emitter -> FirebaseInstant
                .photoReference()
                .child(caption + ".jpg")
                .getDownloadUrl()
                .addOnSuccessListener(emitter::onSuccess)
                .addOnFailureListener(emitter::onError));
    }

    public static Completable delete() {
        return Completable.create(emitter -> FirebaseInstant
                .photoReference()
                .child(FirebaseInstant.user().getUid() + ".jpg")
                .delete()
                .addOnSuccessListener(aVoid -> emitter.onComplete())
                .addOnFailureListener(emitter::onError));
    }
}
