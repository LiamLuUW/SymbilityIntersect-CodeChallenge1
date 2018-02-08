package com.cryptocurrency.liam.symbilityintersectcodechallenge.DB;

/*
 * Database for this app
 */

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.cryptocurrency.liam.symbilityintersectcodechallenge.Model.CryptoCurrency;

/**
 * Database for this app
 */

@Database(entities = {CryptoCurrency.class}, version = 1)
public abstract class AppDataBase extends RoomDatabase {

    private static final String DB_NAME = "appDatabase.db";
    private static volatile AppDataBase dbInstance;

    //using singleton pattern for DB instance
    public static synchronized AppDataBase getInstance(Context context) {
        if (dbInstance == null) {
            dbInstance = createDB(context);
        }
        return dbInstance;
    }

    private static AppDataBase createDB(Context context) {
        return Room.databaseBuilder(context, AppDataBase.class, DB_NAME)
                .fallbackToDestructiveMigration()
                .build();
    }

    public abstract CryptoCurrencyDao getCryptoCurrencyDao();
}