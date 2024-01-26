package com.jilquero.project2;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Phone.class}, version = 1, exportSchema = false)
public abstract class PhoneDatabase  extends RoomDatabase {
    public abstract PhoneDao PhoneDao();
    private static volatile PhoneDatabase INSTANCE;

    static PhoneDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (PhoneDatabase.class){
                if(INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),PhoneDatabase.class,"PhoneDB").addCallback(RoomCallback).fallbackToDestructiveMigration().build();
                }
            }
        }
        return INSTANCE;
    }
    private static final int Threads = 3;
    static final ExecutorService dbExecutor = Executors.newFixedThreadPool(Threads);
    private static RoomDatabase.Callback RoomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            dbExecutor.execute(() -> {
                PhoneDao dao = INSTANCE.PhoneDao();

                dao.insert(new Phone("Samsung", "GalaxyS23 Ultra", 10, "https://www.samsung.com/pl/smartphones/galaxy-s23-ultra/"));
                dao.insert(new Phone("Samsung", "GalaxyA54 5G", 11, "https://www.samsung.com/pl/smartphones/galaxy-a/galaxy-a54-5g-awesome-violet-128gb-sm-a546blvceue/buy/"));
                dao.insert(new Phone("Xiaomi", "Redmi Note 12 Pro", 13, "https://www.mi.com/pl/product/redmi-note-12-pro-5g/"));
            });
        }
    };
}
