package com.studing.bd.crashroads.database.local_database.services;

import android.os.AsyncTask;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.studing.bd.crashroads.CrashRoadsApp;
import com.studing.bd.crashroads.ErrorHandler;
import com.studing.bd.crashroads.database.local_database.LocalDatabase;
import com.studing.bd.crashroads.database.DatabaseMiddleware;
import com.studing.bd.crashroads.database.remote_database.FirebaseInstant;
import com.studing.bd.crashroads.model.User;

public class QueryLocalUserService extends DatabaseMiddleware {

    private final static String TAG = "login Error";

    interface QueryUserCallback {
        void onLocalQuery(User user);
    }

    private LocalDatabase localDatabase;
    private String userId;
    private QueryUserCallback callback;

    public QueryLocalUserService(ErrorHandler errorHandler, QueryUserCallback userCallback) {
        super(errorHandler);
        this.callback = userCallback;
        localDatabase = CrashRoadsApp.getInstance().getDatabase();
        FirebaseUser firebaseUser = FirebaseInstant.user();
        if(firebaseUser != null) {
            userId = firebaseUser.getUid();
        }
    }

    @Override
    public boolean canExecute(User user) {
        return userId != null && userId.length() != 0;
    }

    @Override
    public void execute(User user) {
       loadUser();
    }

    private void loadUser() {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                Log.e(TAG, "load User" );
                User user = localDatabase.userDao().query(userId);
                if(user == null) errorHandler.handleError("No such user");
                else {
                    if (next != null) next.checkNext(user);
                    if (callback != null) callback.onLocalQuery(user);
                }
            }
        });
    }


}
