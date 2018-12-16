package com.studing.bd.crashroads.database.remote_database.services;

import android.net.Uri;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.studing.bd.crashroads.ErrorHandler;
import com.studing.bd.crashroads.database.DatabaseMiddleware;
import com.studing.bd.crashroads.database.remote_database.FirebaseInstant;
import com.studing.bd.crashroads.model.User;

public class DownloadPhotoUriMiddleware extends DatabaseMiddleware {

    private StorageReference storageReference;
    private User user;
    private final static String TAG = "login Error";



    public DownloadPhotoUriMiddleware(ErrorHandler errorHandler) {
        super(errorHandler);
        storageReference = FirebaseInstant.photoReference();
    }

    @Override
    public void execute(User user) {
        this.user = user;
        String caption = user.imageUrl;
        getImageUri(caption);
    }

    @Override
    public boolean canExecute(User user) {
        return user != null && user.imageUrl != null && user.imageUrl.length() != 0;
    }


    private void getImageUri(String caption) {
        Log.e(TAG, "getImageUri: " );
        final StorageReference imageRef = storageReference.child(caption + ".jpg");
        imageRef.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if(task.isSuccessful()) {
                    user.imageUrl = String.valueOf(task.getResult());
                    if(next != null)
                        next.checkNext(user);
                }else {
                    errorHandler.handleError(String.valueOf(task.getException()));
                }

            }
        });
    }
}
