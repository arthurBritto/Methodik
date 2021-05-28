package com.arthurbritto.methodik.model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

/**
 * Data Access Object (DAO) for a task.
 * Each method performs a database operation, such as inserting
 * or deleting a task, running a DB query, or deleting all tasks.
 */

@Dao
public interface TaskDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Task task);

    @Query("DELETE FROM task_table")
    void deleteAll();

    @Delete
    void deleteWord(Task task);

    @Query("SELECT * FROM task_table LIMIT 1")
    Task[] getAnyList();

    @Query("SELECT * FROM task_table ORDER BY task ASC")
    LiveData<List<Task>> getAllWords();

    @Update
    void update(Task... tasks);
}
