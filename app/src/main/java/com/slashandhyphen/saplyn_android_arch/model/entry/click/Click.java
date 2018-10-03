package com.slashandhyphen.saplyn_android_arch.model.entry.click;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by Mike on 12/27/2017.
 */

@Entity(tableName = "clicks")
public class Click {
    @PrimaryKey(autoGenerate = true)
    public Integer id;
    public int foreignId;
    public long time;
    public Integer weight;
    public Integer reps;

    @Ignore
    public Click(int foreignId, long time) {
        this.foreignId = foreignId;
        this.time = time;
        this.weight = null;
        this.reps = null;
    }

    public Click(int foreignId, long time, Integer weight, Integer reps) {
        this.foreignId = foreignId;
        this.time = time;
        this.weight = weight;
        this.reps = reps;
    }
}
