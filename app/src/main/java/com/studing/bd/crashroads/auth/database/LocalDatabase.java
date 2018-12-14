package com.studing.bd.crashroads.auth.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.studing.bd.crashroads.model.User;

@Database(entities = {User.class}, version = 9, exportSchema = false)
public abstract class LocalDatabase extends RoomDatabase {
    public abstract UserDao userDao();
}
