package com.prakash.androidcodeshop.mvvmdemo.databases;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;

import com.prakash.androidcodeshop.mvvmdemo.dao.UserDao;
import com.prakash.androidcodeshop.mvvmdemo.model.User;

@Database(entities = {User.class}, version = 3)
public abstract class MyDatabase extends RoomDatabase{
    public abstract UserDao userDao();

    private static final String DATABASE_NAME = "User";

    // For Singleton instantiation
    private static final Object LOCK = new Object();
    private static volatile MyDatabase sInstance;
//    private Context context;
static final Migration MIGRATION_2_3 = new Migration(2, 3) {
    @Override
    public void migrate(SupportSQLiteDatabase database) {
        // Since we didn't alter the table, there's nothing else to do here.
    }
};
    public static MyDatabase getInstance(Context context) {
        if (sInstance == null) {
            synchronized (LOCK) {
                if (sInstance == null) {
                    sInstance = Room.databaseBuilder(context.getApplicationContext(),
                            MyDatabase.class, MyDatabase.DATABASE_NAME)
                            .allowMainThreadQueries()
                            .addMigrations(MIGRATION_2_3)
                            .build();
                }
            }
        }
        return sInstance;
    }

}
