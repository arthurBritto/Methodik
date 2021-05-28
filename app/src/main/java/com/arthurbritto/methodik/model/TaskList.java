package com.arthurbritto.methodik.model;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

/**
 * Entity class that represents a list of tasks in the database
 */

@Entity(tableName = "taskList_table")
public class TaskList {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    @ColumnInfo(name = "taskList")
    private String mTaskList;

    public TaskList(@NonNull String taskList) {
        this.mTaskList = taskList;
    }

    /*
     * This constructor is annotated using @Ignore, because Room
     * expects only one constructor by default in an entity class.
     */

    @Ignore
    public TaskList(int id, @NonNull String taskList) {
        this.id = id;
        this.mTaskList = taskList;
    }

    public String getList() {
        return this.mTaskList;
    }

    public int getId() {return id;}

    public void setId(int id) {
        this.id = id;
    }
}
