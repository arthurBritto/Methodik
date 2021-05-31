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

@Entity(foreignKeys = @ForeignKey(entity = Panel.class,
        parentColumns = "panelId",
        childColumns = "panelIdTask",
        onDelete = CASCADE))

public class Task {

    @PrimaryKey(autoGenerate = true)
    private int taskId;

    @NonNull
    @ColumnInfo(name = "task")
    private String task;

    //TODO foreign key
    @NonNull
    @ColumnInfo(name = "panel_id_task")
    private int panelIdTask;

    public Task(@NonNull String task) {
        this.task = task;
    }

    /*
     * This constructor is annotated using @Ignore, because Room
     * expects only one constructor by default in an entity class.
     */

    @Ignore
    public Task(int taskId, @NonNull String task, int panelIdTask) {
        this.taskId = taskId;
        this.task = task;
        this.panelIdTask = panelIdTask;
    }

    public String getTask() {
        return this.task;
    }

    public int getId() {
        return taskId;
    }

    public void setId(int id) {
        this.taskId = taskId;
    }

    public int getPanelIdTaskId() {
        return panelIdTask;
    }

    public void setTaskListId(int panelIdTask) {
        this.panelIdTask = panelIdTask;
    }
}
