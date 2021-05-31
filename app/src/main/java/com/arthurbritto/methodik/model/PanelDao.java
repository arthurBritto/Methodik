package com.arthurbritto.methodik.model;

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
    void deleteList(Panel panel);

    @Query("SELECT * FROM panel LIMIT 1")
    Panel[] getAnyList();

    @Transaction
    @Query("SELECT * FROM panel ORDER BY panel ASC")
    List<Panel> getAllPanelLists();

    @Update
    void update(Panel... panelLists);
}
