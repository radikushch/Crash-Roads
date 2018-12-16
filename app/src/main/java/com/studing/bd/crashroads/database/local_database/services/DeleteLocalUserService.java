package com.studing.bd.crashroads.database.local_database.services;

import android.os.AsyncTask;
import android.util.Log;

import com.studing.bd.crashroads.CrashRoadsApp;
import com.studing.bd.crashroads.ErrorHandler;
import com.studing.bd.crashroads.database.local_database.LocalDatabase;
import com.studing.bd.crashroads.database.DatabaseMiddleware;
import com.studing.bd.crashroads.model.User;

public class DeleteLocalUserService extends DatabaseMiddleware {

    private User user;
    private LocalDatabase localDatabase;

    private final static String TAG = "login Error";
    public DeleteLocalUserService(ErrorHandler errorHandler) {
        super(errorHandler);
        localDatabase = CrashRoadsApp.getInstance().getDatabase();
    }

    @Override
    public void execute(User user) {
        this.user = user;
        deleteUser();
    }

    @Override
    public boolean canExecute(User user) {
        return user != null;
    }

    private void deleteUser() {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                Log.e(TAG, "deleteUser");
                localDatabase.userDao().delete(user);
                if(next != null)
                    next.checkNext(user);
            }
        });
    }
}
