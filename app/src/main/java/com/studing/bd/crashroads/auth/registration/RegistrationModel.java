package com.studing.bd.crashroads.auth.registration;

import android.os.AsyncTask;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.studing.bd.crashroads.CrashRoadsApp;
import com.studing.bd.crashroads.auth.database.UserDao;
import com.studing.bd.crashroads.model.User;
import com.studing.bd.crashroads.auth.database.LocalDatabase;

public class RegistrationModel {

    public void saveUserToLocalDatabase(final User user) {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                LocalDatabase db = CrashRoadsApp.getInstance().getDatabase();
                UserDao userDao = db.userDao();
                userDao.insert(user);
                FirebaseAuth.getInstance().signOut();
            }
        });
    }
}
