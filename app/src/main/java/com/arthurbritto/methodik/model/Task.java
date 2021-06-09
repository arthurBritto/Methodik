package com.arthurbritto.methodik.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

/**
 * Entity class that represents a task on a list in the database
 */

@Entity(tableName = "task",
        foreignKeys = {@ForeignKey(entity = Panel.class,
        parentColumns = "id",
        childColumns = "panel_id",
        onDelete = CASCADE)})
public class Task {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    private String name;

    @NonNull
    @ColumnInfo(name = "panel_id")
    private int panelId;

    public Task(@NonNull String name, int panelId) {
        this.name = name;
        this.panelId = panelId;
    }

    @Ignore
    public Task(@NonNull String name) {
        this.name = name;
    }

    /*
     * This constructor is annotated using @Ignore, because Room
     * expects only one constructor by default in an entity class.
     */
    @Ignore
    public Task(int id, @NonNull String name, int panelId) {
        this.id = id;
        this.name = name;
        this.panelId = panelId;
    }

    @Ignore
    public Task(int id, String task_data) {
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

    public int getPanelId() {
        return panelId;
    }

    public void setPanelId(int panelId) {
        this.panelId = panelId;
    }
}
