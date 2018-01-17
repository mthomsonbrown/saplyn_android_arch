package com.slashandhyphen.saplyn_android_arch.model.EntrySet;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.slashandhyphen.saplyn_android_arch.model.entry.click.Click;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.FAIL;

/**
 * Created by Mike on 1/16/2018.
 */

@Dao
public interface EntrySetDao {
    @Insert(onConflict = FAIL)
    void saveEntrySet(EntrySet entrySet);

    @Query("SELECT * FROM entry_sets")
    LiveData<List<EntrySet>> loadAllEntrySets();
}
