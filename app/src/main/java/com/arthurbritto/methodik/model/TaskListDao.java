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
 * Data Access Object (DAO) for a list of tasks.
 * Each method performs a database operation, such as inserting
 * or deleting a list, running a DB query, or deleting all lists.
 */

@Dao
public interface TaskListDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(TaskList taskList);

    @Query("DELETE FROM taskList_table")
    void deleteAll();

    @Delete
    void deleteWord(TaskList taskList);

    @Query("SELECT * FROM taskList_table LIMIT 1")
    TaskList[] getAnyList();

    @Query("SELECT * FROM taskList_table ORDER BY taskList ASC")
    LiveData<List<TaskList>> getAllWords();

    @Update
    void update(TaskList... taskLists);
}
