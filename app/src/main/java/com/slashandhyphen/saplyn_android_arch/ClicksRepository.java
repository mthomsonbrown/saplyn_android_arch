package com.slashandhyphen.saplyn_android_arch;

import android.arch.lifecycle.LiveData;
import android.util.Log;

import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static android.content.ContentValues.TAG;

/**
 * Created by Mike on 12/27/2017.
 */

public class ClicksRepository {
    private final EntryDatabase database;

    public ClicksRepository(EntryDatabase database) {
        this.database = database;
    }

    public LiveData<List<Click>> getClicks() {

        return database.loadAllClicks();
    }

    public void addClick(Click click) {
        database.saveClick(click)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(thing -> Log.e(TAG, "Success writing to db"),
                        () -> Log.e(TAG, "Unable to update username"));
    }
}
