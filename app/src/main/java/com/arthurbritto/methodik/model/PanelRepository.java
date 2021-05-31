package com.arthurbritto.methodik.model;

import android.app.Application;
import android.os.AsyncTask;
import androidx.lifecycle.LiveData;
import java.util.List;

/**
 * This class holds the implementation code for the methods that interact with the database.
 * Using a repository allows us to group the implementation methods together,
 * and allows the PanelViewModel to be a clean interface between the rest of the app
 * and the database.
 *
 * For insert, update and delete, and longer-running queries,
 * you must run the database interaction methods in the background.
 *
 * Typically, all you need to do to implement a database method
 * is to call it on the data access object (DAO), in the background if applicable.
 */

public class PanelRepository {

    private PanelDao panelDao;
    private LiveData<List<Panel>> allLists;  // A panel have many Lists

    public PanelRepository(Application application) {
        MethodikRoomDatabase db = MethodikRoomDatabase.getDatabase(application);
        panelDao = db.panelDao();
        allLists= panelDao.getAllPanelLists();
    }

    public LiveData<List<Panel>> getAllLists() {
        return allLists;
    }

    public void insert(Panel panel) {
        new insertAsyncTask(panelDao).execute(panel);
    }

    public void update(Panel panel)  {
        new updatePanelListAsyncTask(panelDao).execute(panel);
    }

    public void deleteAllPanelLists()  {
        new deleteAllPanelListsAsyncTask(panelDao).execute();
    }

    // Must run off main thread
    public void deletePanelList(Panel panel) {
        new deletePanelListAsyncTask(panelDao).execute(panel);
    }

    // Static inner classes below here to run database interactions in the background.

    /**
     * Inserts a new List on the Panel into the database.
     */

    private static class insertAsyncTask extends AsyncTask<Panel, Void, Void> {

        private PanelDao asyncTaskDao;

        insertAsyncTask(PanelDao dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Panel... params) {
            asyncTaskDao.insert(params[0]);
            return null;
        }
    }

    /**
     * Deletes all Panel Lists from the database (does not delete the table).
     */

    private static class deleteAllPanelListsAsyncTask extends AsyncTask<Void, Void, Void> {
        private PanelDao asyncTaskDao;

        deleteAllPanelListsAsyncTask(PanelDao dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            asyncTaskDao.deleteAll();
            return null;
        }
    }

    /**
     *  Deletes a single List in the Panel from the database.
     */
    private static class deletePanelListAsyncTask extends AsyncTask<Panel, Void, Void> {
        private PanelDao asyncTaskDao;

        deletePanelListAsyncTask(PanelDao dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Panel... params) {
            asyncTaskDao.deletePanelList(params[0]);
            return null;
        }
    }

    /**
     *  Updates a List on the Panel in the database.
     */
    private static class updatePanelListAsyncTask extends AsyncTask<Panel, Void, Void> {
        private PanelDao asyncTaskDao;

        updatePanelListAsyncTask(PanelDao dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Panel... params) {
            asyncTaskDao.update(params[0]);
            return null;
        }
    }
}