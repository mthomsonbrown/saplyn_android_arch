package com.slashandhyphen.saplyn_android_arch.model.entry.click;

import android.arch.lifecycle.LiveData;
import android.util.Log;

import com.slashandhyphen.saplyn_android_arch.model.Database;

import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static android.content.ContentValues.TAG;

/**
 * Created by Mike on 12/27/2017.
 */

public class ClickRepository {
    private final Database database;

    public ClickRepository(Database database) {
        this.database = database;
    }

    public LiveData<List<Click>> getClicks() {

        return database.loadAllClicks();
    }

    public LiveData<List<Click>> getClicksInRange(long timeStart, long timeEnd) {
        return database.loadClicksInRange(timeStart, timeEnd);
    }

    public void addClick(Click click) {
        database.saveClick(click)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(thing -> Log.e(TAG, "Success writing to db"),
                        () -> Log.e(TAG, "Unable to update username"));
    }
}
