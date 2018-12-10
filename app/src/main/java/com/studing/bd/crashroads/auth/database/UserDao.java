package com.studing.bd.crashroads.auth.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.studing.bd.crashroads.model.User;

@Dao
public interface UserDao {

    @Query("SELECT * FROM user WHERE uid = :id")
    User query(String id);

    @Insert
    void insert(User user);

    @Delete
    void delete(User user);
}
