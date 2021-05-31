package com.arthurbritto.methodik.model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;

/**
 * Data Access Object (DAO) for a Panel of lists of tasks.
 * Each method performs a database operation, such as inserting
 * or deleting a list, running a DB query, or deleting all lists.
 */

@Dao
public interface PanelDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Panel panel);

    @Query("DELETE FROM panel")
    void deleteAll();

    @Delete
    void deletePanel(Panel panel);

    @Query("SELECT * FROM panel LIMIT 1")
    Panel[] getAnyPanels();

    @Transaction
    @Query("SELECT * FROM panel ORDER BY name ASC")
    LiveData<List<Panel>> getAllPanels();

    @Update
    void update(Panel... panels);

}
