package com.slashandhyphen.saplyn_android_arch.model.model.click;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by Mike on 12/27/2017.
 */

@Entity(tableName = "clicks")
public class Click {
    @PrimaryKey(autoGenerate = true)
    public Integer id;
    public long time;

    public Click(long time) {
        this.time = time;
    }
}
