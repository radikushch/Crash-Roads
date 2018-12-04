package com.studing.bd.crashroads.auth.login;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.studing.bd.crashroads.User;

public class LoginModel {

    private FirebaseDatabase database;
    private DatabaseReference reference;

    public LoginModel() {
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("users");
    }

    public void createUser(FirebaseUser firebaseUser) {
        User user = User.builder()
                .username(firebaseUser.getDisplayName())
                .email(firebaseUser.getEmail())
                .image(String.valueOf(firebaseUser.getPhotoUrl()))
                .build();
        String userId = firebaseUser.getUid();
        addToDatabase(user, userId);
    }

    private void addToDatabase(User user, String userId) {
        reference.child(userId).setValue(user);
    }
}
