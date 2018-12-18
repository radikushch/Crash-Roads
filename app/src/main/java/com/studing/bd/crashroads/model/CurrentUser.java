package com.studing.bd.crashroads.model;

import com.studing.bd.crashroads.database.remote_database.FirebaseInstant;

public class CurrentUser {

    private static User currentUser;

    private CurrentUser() {

    }

    public static void set(User user) {
        currentUser = user;
        currentUser.uid = FirebaseInstant.user().getUid();
    }

    public static void delete() {
        currentUser = null;
    }

    public static User get() {
        if(currentUser == null) return new User.UserBuilder().build();
        else                    return currentUser;
    }
}
