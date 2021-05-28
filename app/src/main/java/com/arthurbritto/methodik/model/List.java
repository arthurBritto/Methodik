package com.arthurbritto.methodik.model;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

/**
 * Entity class that represents a list in the database
 */

@Entity(tableName = "list_table")
public class List {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    @ColumnInfo(name = "list")
    private String mList;

    public List(@NonNull String list) {
        this.mList = list;
    }

    /*
     * This constructor is annotated using @Ignore, because Room
     * expects only one constructor by default in an entity class.
     */

    @Ignore
    public List(int id, @NonNull String list) {
        this.id = id;
        this.mList = list;
    }

    public String getList() {
        return this.mList;
    }

    public int getId() {return id;}

    public void setId(int id) {
        this.id = id;
    }
}
