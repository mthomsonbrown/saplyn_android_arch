package com.slashandhyphen.saplyn_android_arch.model.EntrySet;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by Mike on 1/16/2018.
 */

@Entity(tableName = "entry_sets")
public class EntrySet {
    @PrimaryKey(autoGenerate = true)
    public Integer id;
    public String name;
    public long timeCreated;
    public long timeModified;

    public EntrySet(String name) {
        this.name = name;
        this.timeCreated = System.currentTimeMillis();
        this.timeModified = this.timeCreated;
    }
}
