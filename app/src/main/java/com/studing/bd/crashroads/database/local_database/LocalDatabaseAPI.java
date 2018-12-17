package com.studing.bd.crashroads.database.local_database;

import com.studing.bd.crashroads.CrashRoadsApp;
import com.studing.bd.crashroads.model.User;

import io.reactivex.Single;

public class LocalDatabaseAPI {

    public static void insert(User user) {
        CrashRoadsApp.getInstance().getDatabase().userDao().insert(user);
    }

    public static void delete(User user) {
        CrashRoadsApp.getInstance().getDatabase().userDao().delete(user);
    }

    public static Single<User> query(String id) {
        return CrashRoadsApp.getInstance().getDatabase().userDao().query(id);
    }

    public static void deleteAll() {
        CrashRoadsApp.getInstance().getDatabase().userDao().deleteAll();
    }
}
