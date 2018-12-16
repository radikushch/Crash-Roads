package com.studing.bd.crashroads.database.local_database.services;

import android.os.AsyncTask;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.studing.bd.crashroads.CrashRoadsApp;
import com.studing.bd.crashroads.ErrorHandler;
import com.studing.bd.crashroads.database.DatabaseMiddleware;
import com.studing.bd.crashroads.database.local_database.LocalDatabase;
import com.studing.bd.crashroads.database.local_database.UserDao;
import com.studing.bd.crashroads.database.remote_database.FirebaseInstant;
import com.studing.bd.crashroads.model.User;

public class SaveLocalUserService extends DatabaseMiddleware {


    public SaveLocalUserService(ErrorHandler errorHandler) {
        super(errorHandler);
    }

    private final static String TAG = "login Error";


    @Override
    public boolean canExecute(User user) {
        return user != null && FirebaseInstant.user() != null;
    }

    @Override
    public void execute(User user) {
        saveToLocalDatabase(user);
    }

    private void saveToLocalDatabase(final User user) {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                Log.e(TAG, "save local");
                LocalDatabase db = CrashRoadsApp.getInstance().getDatabase();
                UserDao userDao = db.userDao();
                userDao.insert(user);
                User saved = userDao.query(user.uid);
                if(saved != null) {
                    if(next != null) checkNext(user);
                }else {
                    errorHandler.handleError("Failed! Try to sign up again");
                }

            }
        });
    }
}
