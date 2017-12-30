package com.slashandhyphen.saplyn_android_arch.model;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.NonNull;

import com.slashandhyphen.saplyn_android_arch.model.entry.click.Click;
import com.slashandhyphen.saplyn_android_arch.model.entry.click.ClickDao;

import java.util.List;

import rx.Completable;

/**
 * Created by Mike on 12/27/2017.
 */

@android.arch.persistence.room.Database(entities = {Click.class}, version = 1)
public abstract class Database extends RoomDatabase {
    public abstract ClickDao clickDao();
    private static Database dbInstance;
    private static final String DATABASE_NAME = "entry-db";

    private final MutableLiveData<Boolean> mIsDatabaseCreated = new MutableLiveData<>();

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

    public Completable saveClick(Click click) {
        return Completable.fromAction(() -> {
            dbInstance.clickDao().saveClick(click);
        });
    }
}
