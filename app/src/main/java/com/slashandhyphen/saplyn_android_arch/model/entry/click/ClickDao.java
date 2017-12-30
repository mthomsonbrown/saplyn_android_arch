package com.slashandhyphen.saplyn_android_arch.model.entry.click;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

/**
 * Created by Mike on 12/27/2017.
 */

@Dao
public interface ClickDao {
    @Insert(onConflict = REPLACE)
    void saveClick(Click click);

    @Query("SELECT * FROM clicks")
    LiveData<List<Click>> loadAllClicks();

    @Query(("SELECT * FROM clicks WHERE time BETWEEN :timeStart AND :timeEnd"))
    LiveData<List<Click>> loadClicksInRange(long timeStart, long timeEnd);
}
