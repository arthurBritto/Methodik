package com.arthurbritto.methodik.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

/**
 * Entity class that represents a Panel of lists of tasks in the database
 */

@Entity(tableName = "panel")
public class Panel {

    @PrimaryKey(autoGenerate = true)
    private int panelId;

    @NonNull
    @ColumnInfo(name = "panel")
    private String listName;

    public Panel(@NonNull String listName) {
        this.listName = listName;
    }

    /*
     * This constructor is annotated using @Ignore, because Room
     * expects only one constructor by default in an entity class.
     */

    @Ignore
    public Panel(int panelId, @NonNull String listName) {
        this.panelId = panelId;
        this.listName = listName;
    }

    public String getListName() {
        return this.listName;
    }

    public int getId() {
        return panelId;
    }

    public void setId(int id) {
        this.panelId = id;
    }
}
