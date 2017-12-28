package com.slashandhyphen.saplyn_android_arch;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.NonNull;

import java.util.List;

import rx.Completable;

/**
 * Created by Mike on 12/27/2017.
 */

@Database(entities = {Click.class}, version = 1)
public abstract class EntryDatabase extends RoomDatabase {
    public abstract ClickDao clickDao();
    private static EntryDatabase dbInstance;
    private static final String DATABASE_NAME = "entry-db";

    private final MutableLiveData<Boolean> mIsDatabaseCreated = new MutableLiveData<>();

    public static EntryDatabase getInstance(final Context context) {
        if (dbInstance == null) {
            synchronized (EntryDatabase.class) {
                if (dbInstance == null) {
                    dbInstance = buildDatabase(context.getApplicationContext());
                    dbInstance.updateDatabaseCreated(context.getApplicationContext());
                }
            }
        }
        return dbInstance;
    }

    private static EntryDatabase buildDatabase(final Context appContext) {
        return Room.databaseBuilder(appContext, EntryDatabase.class, DATABASE_NAME)
                .addCallback(new Callback() {
                    @Override
                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
                        super.onCreate(db);
                    }
                }).build();
    }

    private void updateDatabaseCreated(final Context context) {
        if (context.getDatabasePath(DATABASE_NAME).exists()) {
            setDatabaseCreated();
        }
    }

    private void setDatabaseCreated(){
        mIsDatabaseCreated.postValue(true);
    }

    public LiveData<List<Click>> loadAllClicks() {
        return dbInstance.clickDao().loadAllClicks();
    }

    public Completable saveClick() {
        return Completable.fromAction(() -> {
            dbInstance.clickDao().saveClick(new Click(System.currentTimeMillis()));
        });
    }
}
