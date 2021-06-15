package com.arthurbritto.methodik.model;

import android.os.Parcel;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

/**
 * Entity class that represents a Panel of lists of tasks in the database
 */

@Entity(tableName = "panel")
public class Panel{

    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    private String name;

    public Panel(@NonNull String name) {
        this.name = name;
    }

    /*
     * This constructor is annotated using @Ignore, because Room
     * expects only one constructor by default in an entity class.
     */
    @Ignore
    public Panel(int id, @NonNull String name) {
        this.id = id;
        this.name = name;
    }

    protected Panel(Parcel in) {
        id = in.readInt();
        name = in.readString();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }
}
