package com.studing.bd.crashroads;

import android.app.Application;
import android.arch.persistence.room.Room;

import com.facebook.stetho.Stetho;
import com.studing.bd.crashroads.auth.database.LocalDatabase;

public class CrashRoadsApp extends Application {

    private static CrashRoadsApp instance;
    private LocalDatabase database;

    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.InitializerBuilder initializerBuilder =
                Stetho.newInitializerBuilder(this);
        initializerBuilder.enableWebKitInspector(
                Stetho.defaultInspectorModulesProvider(this)
        );
        initializerBuilder.enableDumpapp(
                Stetho.defaultDumperPluginsProvider(this)
        );
        Stetho.Initializer initializer = initializerBuilder.build();
        Stetho.initialize(initializer);
        instance = this;
        database = Room.databaseBuilder(this, LocalDatabase.class, "database")
                .fallbackToDestructiveMigration()
                .build();
    }

    public static CrashRoadsApp getInstance() {
        return instance;
    }

    public LocalDatabase getDatabase() {
        return database;
    }
}
