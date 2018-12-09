package com.studing.bd.crashroads.auth.login.authChain.local;

import android.os.AsyncTask;

import com.studing.bd.crashroads.CrashRoadsApp;
import com.studing.bd.crashroads.auth.database.LocalDatabase;
import com.studing.bd.crashroads.auth.login.authChain.LoginMiddleware;
import com.studing.bd.crashroads.model.User;

public class DeleteLocalUserMiddleware extends LoginMiddleware {

    private User user;
    private LocalDatabase localDatabase;

    public DeleteLocalUserMiddleware(LoginErrorNotificator loginErrorNotificator) {
        super(loginErrorNotificator);
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
                localDatabase.userDao().delete(user);
                if(next != null)
                    next.checkNext(user);
            }
        });
    }
}
