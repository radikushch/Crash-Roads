package com.studing.bd.crashroads.auth.login.authChain.local;

import android.os.AsyncTask;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.studing.bd.crashroads.CrashRoadsApp;
import com.studing.bd.crashroads.auth.database.LocalDatabase;
import com.studing.bd.crashroads.auth.login.authChain.LoginMiddleware;
import com.studing.bd.crashroads.model.User;

public class LoadLocalUserMiddleware extends LoginMiddleware {

    private LocalDatabase localDatabase;
    private String userId;

    public LoadLocalUserMiddleware(LoginErrorNotificator loginErrorNotificator) {
        super(loginErrorNotificator);
        localDatabase = CrashRoadsApp.getInstance().getDatabase();
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if(firebaseUser != null) {
            userId = firebaseUser.getUid();
            Log.e("login", "LoadLocalUserMiddleware: " + userId );
        }
    }

    @Override
    public boolean canExecute(User user) {
        Log.e("login", "canExecute:");
        return userId != null && userId.length() != 0;
    }

    @Override
    public void execute(User user) {
        Log.e("login", "LoadLocalUserMiddleware: " + userId );
       loadUser();
    }

    private void loadUser() {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                Log.e("login", "run: " );
                User user = localDatabase.userDao().query(userId);
                if(user == null) loginErrorNotificator.handleChainError("No such user");
                else {
                    Log.e("login", "loadUser: " + user.email);
                    if(next != null)
                        next.checkNext(user);
                }
            }
        });
    }


}
