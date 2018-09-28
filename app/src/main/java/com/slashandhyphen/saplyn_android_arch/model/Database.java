package com.slashandhyphen.saplyn_android_arch.model;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;
import android.support.annotation.NonNull;

import com.slashandhyphen.saplyn_android_arch.model.EntrySet.EntrySet;
import com.slashandhyphen.saplyn_android_arch.model.EntrySet.EntrySetDao;
import com.slashandhyphen.saplyn_android_arch.model.entry.click.Click;
import com.slashandhyphen.saplyn_android_arch.model.entry.click.ClickDao;

import java.util.List;

import rx.Completable;

/**
 * Created by Mike on 12/27/2017.
 */

@android.arch.persistence.room.Database(entities = {Click.class, EntrySet.class}, version = 2)
public abstract class Database extends RoomDatabase {
    private static Database dbInstance;

    public abstract ClickDao clickDao();
    public abstract EntrySetDao entrySetDao();

    private static final String DATABASE_NAME = "entry-db";

    private final MutableLiveData<Boolean> mIsDatabaseCreated = new MutableLiveData<>();

    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE clicks ADD COLUMN foreignId INTEGER");
        }
    };

    public static Database getInstance(final Context context) {
        if (dbInstance == null) {
            synchronized (Database.class) {
                if (dbInstance == null) {
                    dbInstance = buildDatabase(context.getApplicationContext());
                    dbInstance.updateDatabaseCreated(context.getApplicationContext());
                }
            }
        }
        return dbInstance;
    }

    private static Database buildDatabase(final Context appContext) {
        return Room.databaseBuilder(appContext, Database.class, DATABASE_NAME)
                .addMigrations(MIGRATION_1_2)
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

    // Click methods
    public LiveData<List<Click>> loadAllClicks() {
        return dbInstance.clickDao().loadAllClicks();
    }

    public LiveData<List<Click>> loadClicksInRange(long timeStart, long timeEnd) {
        return dbInstance.clickDao().loadClicksInRange(timeStart, timeEnd);
    }

    public Completable saveClick(Click click) {
        return Completable.fromAction(() -> {
            dbInstance.clickDao().saveClick(click);
        });
    }

    // Entryset methods
    public LiveData<List<EntrySet>> loadAllEntrySets() {
        return dbInstance.entrySetDao().loadAllEntrySets();
    }

    public Completable saveEntrySet(EntrySet entrySet) {
        return Completable.fromAction(() -> {
            dbInstance.entrySetDao().saveEntrySet(entrySet);
        });
    }
}
