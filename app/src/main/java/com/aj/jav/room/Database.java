package com.aj.jav.room;

import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.aj.jav.room.dao.MainListDao;
import com.aj.jav.room.dao.MainListEntity;

/**
 * 有新增Entity記得要列入
 */
@android.arch.persistence.room.Database(entities = { MainListEntity.class}, version = 1, exportSchema = false)
public abstract class Database extends RoomDatabase {
    private static volatile Database INSTANCE;

    public abstract MainListDao mainListDao();

    public static Database getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (Database.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), Database.class, "jav.db")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}