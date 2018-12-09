package com.studing.bd.crashroads.auth.login.authChain;


import android.util.Log;

import com.studing.bd.crashroads.model.User;

public abstract class LoginMiddleware {

    public interface LoginErrorNotificator {
        void handleChainError(String error);
    }

    protected LoginMiddleware next;
    protected LoginErrorNotificator loginErrorNotificator;

    public LoginMiddleware(LoginErrorNotificator loginErrorNotificator) {
        this.loginErrorNotificator = loginErrorNotificator;
    }

    public void linkWith(LoginMiddleware next) {
        this.next = next;
    }

    public void checkNext(User user) {
        Log.e("login", "checkNext " );
        if(canExecute(user)) {
            Log.e("login", "checkNext if " );
            execute(user);
        }
    }

    public abstract void execute(User user);
    public abstract boolean canExecute(User user);
}
