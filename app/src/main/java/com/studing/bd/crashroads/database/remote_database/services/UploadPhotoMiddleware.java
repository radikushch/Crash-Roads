package com.studing.bd.crashroads.database.remote_database.services;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.studing.bd.crashroads.ErrorHandler;
import com.studing.bd.crashroads.Utils;
import com.studing.bd.crashroads.database.DatabaseMiddleware;
import com.studing.bd.crashroads.database.remote_database.FirebaseInstant;
import com.studing.bd.crashroads.model.User;

public class UploadPhotoMiddleware extends DatabaseMiddleware {

    private StorageReference storageReference;
    private User user;

    private final static String TAG = "login Error";

    public UploadPhotoMiddleware(ErrorHandler errorHandler){
        super(errorHandler);
        storageReference = FirebaseInstant.photoReference();
    }

    @Override
    public void execute(User user) {
        this.user = user;
        uploadPhoto(user);
    }

    @Override
    public boolean canExecute(User user) {
        return user != null && user.imageByte != null && user.imageByte.length != 0;
    }

    private void uploadPhoto(User user) {
        byte[] data = user.imageByte;
        String caption = user.name + Utils.getDateStamp();
        uploadPhotoToFireBaseStorage(data, caption);
    }

    private void uploadPhotoToFireBaseStorage(byte[] data, final String caption) {
        Log.e(TAG, "uploadPhotoToFireBaseStorage: " );
        final StorageReference imageRef = storageReference.child(caption + ".jpg");
        final UploadTask uploadTask = imageRef.putBytes(data);
        uploadTask.addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if(task.isSuccessful()) {
                    user.imageUrl = caption;
                    Log.e("login", "uploadPhoto: " + user.imageUrl );
                    if(next != null)
                        next.checkNext(user);
                }else {
                    errorHandler.handleError(String.valueOf(task.getException()));
                }

            }
        });
    }
}