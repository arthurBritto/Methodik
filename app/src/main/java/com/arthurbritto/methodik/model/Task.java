package com.arthurbritto.methodik.model;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

/**
 * Entity class that represents a task on a list in the database
 */

@Entity(tableName = "task_table")
public class Task {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    @ColumnInfo(name = "task")
    private String mTask;

    public Task(@NonNull String task) {
        this.mTask = task;
    }

    /*
     * This constructor is annotated using @Ignore, because Room
     * expects only one constructor by default in an entity class.
     */

    @Ignore
    public Task(int id, @NonNull String task) {
        this.id = id;
        this.mTask = task;
    }

    public String getTask() {
        return this.mTask;
    }

    public int getId() {return id;}

    public void setId(int id) {
        this.id = id;
    }
}
