package com.example.dietmanagement;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {EntityWeight.class}, version = 2, exportSchema = false)
public abstract class DBWeight extends RoomDatabase {
    private static DBWeight instance;
    public abstract DaoWeight weightDao();
    public static synchronized DBWeight getDatabase(Context context) {
        if (instance == null) {
            //create a RoomDatabase.Builder object, for creating the database
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            DBWeight.class, "weight_database")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }
}