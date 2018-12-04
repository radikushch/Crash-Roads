package com.studing.bd.crashroads.auth.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.studing.bd.crashroads.User;

@Database(entities = {User.class}, version = 1)
public abstract class LocalDatabase extends RoomDatabase {
    public abstract UserDao userDao();
}
