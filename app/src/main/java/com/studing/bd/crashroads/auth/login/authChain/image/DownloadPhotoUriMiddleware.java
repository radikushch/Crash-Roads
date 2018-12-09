package com.studing.bd.crashroads.auth.login.authChain.image;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.studing.bd.crashroads.auth.login.authChain.LoginMiddleware;
import com.studing.bd.crashroads.model.User;

public class DownloadPhotoUriMiddleware extends LoginMiddleware {

    private StorageReference storageReference;
    private User user;


    public DownloadPhotoUriMiddleware(LoginErrorNotificator loginErrorNotificator) {
        super(loginErrorNotificator);
        FirebaseStorage storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference("users_photos");
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
        final StorageReference imageRef = storageReference.child(caption + ".jpg");
        imageRef.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if(task.isSuccessful()) {
                    user.imageUrl = String.valueOf(task.getResult());
                    Log.e("login", "getUri: " + user.imageUrl );
                    if(next != null)
                        next.checkNext(user);
                }else {
                    loginErrorNotificator.handleChainError(String.valueOf(task.getException()));
                }

            }
        });
    }
}
