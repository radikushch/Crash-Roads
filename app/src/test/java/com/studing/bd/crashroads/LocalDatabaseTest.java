package com.studing.bd.crashroads;

import android.arch.persistence.room.Room;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.studing.bd.crashroads.database.local_database.LocalDatabase;
import com.studing.bd.crashroads.database.local_database.UserDao;
import com.studing.bd.crashroads.model.User;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class LocalDatabaseTest {

    private LocalDatabase db;
    private UserDao userDao;

    @Before
    public void onCreateDb() throws Exception {
        db = Room.inMemoryDatabaseBuilder(
                InstrumentationRegistry.getContext(),
                LocalDatabase.class
        ).build();
        userDao = db.userDao();
    }

    @After
    public void closeDb() throws Exception {
        db.close();
    }

    @Test
    public void queryNotExistsObject() throws Exception {
        User user = userDao.query("sdasd");
        assertNull(user);
    }
}
