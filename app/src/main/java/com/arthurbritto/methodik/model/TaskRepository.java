package com.arthurbritto.methodik.model;

import android.app.Application;
import android.os.AsyncTask;
import androidx.lifecycle.LiveData;
import java.util.List;

    /**
     * This class holds the implementation code for the methods that interact with the database.
     * Using a repository allows us to group the implementation methods together,
     * and allows the TaskViewModel to be a clean interface between the rest of the app
     * and the database.
     *
     * For insert, update and delete, and longer-running queries,
     * you must run the database interaction methods in the background.
     *
     * Typically, all you need to do to implement a database method
     * is to call it on the data access object (DAO), in the background if applicable.
     */

    public class TaskRepository {

        private TaskDao taskDao;
        private LiveData<List<Task>> allTasks;

        public TaskRepository(Application application) {
            MethodikRoomDatabase db = MethodikRoomDatabase.getDatabase(application);
            taskDao = db.taskDao();
            allTasks = taskDao.getAllTasks();
        }

        public LiveData<List<Task>> getAllTasks() {
            return allTasks;
        }

        public void insert(Task task) {
            new insertAsyncTask(taskDao).execute(task);
        }

        public void update(Task task)  {
            new updateTaskAsyncTask(taskDao).execute(task);
        }

        public void deleteAllTaskes()  {
            new deleteAllTasksAsyncTask(taskDao).execute();
        }

        // Must run off main thread
        public void deleteTask(Task task) {
            new deleteTaskAsyncTask(taskDao).execute(task);
        }

        // Static inner classes below here to run database interactions in the background.
        /**
         * Inserts a new task into the database.
         */
        private static class insertAsyncTask extends AsyncTask<Task, Void, Void> {

            private TaskDao asyncTaskDao;

            insertAsyncTask(TaskDao dao) {
                asyncTaskDao = dao;
            }

            @Override
            protected Void doInBackground(final Task... params) {
                asyncTaskDao.insert(params[0]);
                return null;
            }
        }

        /**
         * Deletes all tasks from the database (does not delete the table).
         */
        private static class deleteAllTasksAsyncTask extends AsyncTask<Void, Void, Void> {
            private TaskDao asyncTaskDao;

            deleteAllTasksAsyncTask(TaskDao dao) {
                asyncTaskDao = dao;
            }

            @Override
            protected Void doInBackground(Void... voids) {
                asyncTaskDao.deleteAll();
                return null;
            }
        }

        /**
         *  Deletes a single task from the database.
         */
        private static class deleteTaskAsyncTask extends AsyncTask<Task, Void, Void> {
            private TaskDao asyncTaskDao;

            deleteTaskAsyncTask(TaskDao dao) {
                asyncTaskDao = dao;
            }

            @Override
            protected Void doInBackground(final Task... params) {
                asyncTaskDao.deleteTask(params[0]);
                return null;
            }
        }

        /**
         *  Updates a task in the database.
         */
        private static class updateTaskAsyncTask extends AsyncTask<Task, Void, Void> {
            private TaskDao asyncTaskDao;

            updateTaskAsyncTask(TaskDao dao) {
                asyncTaskDao = dao;
            }

            @Override
            protected Void doInBackground(final Task... params) {
                asyncTaskDao.update(params[0]);
                return null;
            }
        }
    }
