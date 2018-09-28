package com.slashandhyphen.saplyn_android_arch.model.entry.click;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by Mike on 12/27/2017.
 */

@Entity(tableName = "clicks")
public class Click {
    @PrimaryKey(autoGenerate = true)
    public Integer id;
    public Integer foreignId;
    public long time;

    public Click(int foreignId, long time) {
        this.foreignId = foreignId;
        this.time = time;
    }
}
