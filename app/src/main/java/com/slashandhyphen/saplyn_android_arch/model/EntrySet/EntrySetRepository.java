package com.slashandhyphen.saplyn_android_arch.model.EntrySet;

import android.arch.lifecycle.LiveData;
import android.util.Log;

import com.slashandhyphen.saplyn_android_arch.model.Database;

import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static android.content.ContentValues.TAG;

/**
 * Created by Mike on 1/16/2018.
 */

public class EntrySetRepository {
    private final Database database;

    public EntrySetRepository(Database database) {
        this.database = database;
    }

    public LiveData<List<EntrySet>> getEntrySets() {

        return database.loadAllEntrySets();
    }

    public void addEntrySet(EntrySet entrySet) {
        database.saveEntrySet(entrySet)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                // TODO: This returns what I think is an error message on success...
                .subscribe(thing -> Log.e(TAG, "Success writing to db"),
                        () -> Log.e(TAG, "Unable to save entry set"));
    }
}
