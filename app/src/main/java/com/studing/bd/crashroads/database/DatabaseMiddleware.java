package com.studing.bd.crashroads.database;

import com.studing.bd.crashroads.ErrorHandler;
import com.studing.bd.crashroads.model.User;

public abstract class DatabaseMiddleware {

    protected DatabaseMiddleware next;
    protected ErrorHandler errorHandler;

    public DatabaseMiddleware(ErrorHandler errorHandler) {
        this.errorHandler = errorHandler;
    }

    public void linkWith(DatabaseMiddleware next) {
        this.next = next;
    }

    public void checkNext(User user) {
        if(canExecute(user)) {
            execute(user);
        }else {
            next.checkNext(user);
        }
    }

    public abstract void execute(User user);
    public abstract boolean canExecute(User user);
}
