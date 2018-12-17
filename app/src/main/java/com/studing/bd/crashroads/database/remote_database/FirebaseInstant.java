package com.studing.bd.crashroads.database.remote_database;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class FirebaseInstant {

    private final static String REMOTE_USER_TABLE_NAME = "users";
    private final static String REMOTE_USER_PHOTO_TABLE_NAME = "users_photos";

    public static FirebaseAuth auth() {
        return FirebaseAuth.getInstance();
    }

    public static FirebaseDatabase database() {
        return FirebaseDatabase.getInstance();
    }

    public static DatabaseReference userReference() {
        return FirebaseDatabase.getInstance().getReference().child(REMOTE_USER_TABLE_NAME);
    }

    public static StorageReference photoReference() {
        return FirebaseStorage.getInstance().getReference(REMOTE_USER_PHOTO_TABLE_NAME);
    }

    public static FirebaseUser user() {
        return FirebaseAuth.getInstance().getCurrentUser();
    }



}
