package com.studing.bd.crashroads.auth.registration;

import android.net.Uri;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class RegistrationModel {

    interface UriCallback {
        void apply(Uri uri);
    }

    private FirebaseStorage storage;
    private StorageReference storageReference;
    private UriCallback callback;

    public RegistrationModel() {
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference("users_photos");
    }

    public void uploadPhoto(byte[] data, String caption, UriCallback callback) {
        this.callback = callback;
        uploadPhotoToFireBaseStorage(data, caption);

    }

    private void uploadPhotoToFireBaseStorage(byte[] data, final String caption) {
        final StorageReference imageRef = storageReference.child(caption + ".jpg");
        final UploadTask uploadTask = imageRef.putBytes(data);
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                getImageUri(caption);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
            }
        });
    }

    private void getImageUri(String caption) {
        StorageReference imageRef = storageReference.child(caption + ".jpg");
        imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
               callback.apply(uri);
            }
        });
    }
}
