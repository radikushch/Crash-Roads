package com.studing.bd.crashroads.database.local_database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.studing.bd.crashroads.model.User;

import io.reactivex.Single;

@Dao
public interface UserDao {

    @Query("SELECT * FROM user WHERE uid = :id")
    Single<User> query(String id);

    @Insert
    void insert(User user);

    @Delete
    void delete(User user);

}
