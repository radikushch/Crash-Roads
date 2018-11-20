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
        User user = new User();
        user.setUsername(firebaseUser.getDisplayName());
        user.setEmail(firebaseUser.getEmail());
        user.setImage(String.valueOf(firebaseUser.getPhotoUrl()));
        String userId = firebaseUser.getUid();
        addToDatabase(user, userId);
    }

    private void addToDatabase(User user, String userId) {
        reference.child(userId).setValue(user);
    }
}
