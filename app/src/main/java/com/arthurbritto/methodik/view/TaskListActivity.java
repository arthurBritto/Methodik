package com.arthurbritto.methodik.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.arthurbritto.methodik.R;
import com.arthurbritto.methodik.model.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class TaskListActivity extends AppCompatActivity {

    public static final int NEW_TASK_ACTIVITY_REQUEST_CODE = 1;
    public static final int UPDATE_TASK_ACTIVITY_REQUEST_CODE = 2;
    public static final int SHOW_EDIT_TASK_ACTIVITY_REQUEST_CODE = 3;

    public static final String EXTRA_DATA_NAME = "extra_data_name";
    public static final String EXTRA_DATA_ID = "extra_data_id";
    public static final String EXTRA_DATA_UPDATE_TASK = "extra_data_update_task";
    public static final String EXTRA_REPLY = "com.example.android.Methodik.REPLY";
    public static final String EXTRA_REPLY_ID = "com.android.example.Methodik.REPLY_ID";

    private TaskViewModel taskViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);

        // Set up the RecyclerView.
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final TaskAdapter adapter = new TaskAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Set up the PanelViewModel.
        taskViewModel = ViewModelProviders.of(this).get(TaskViewModel.class);
        // Get all the tasks from the database
        // and associate them to the adapter.
        taskViewModel.getAllTasks().observe(this, (List<Task> tasks) -> {
            // Update the cached copy of the tasks in the adapter.
            adapter.setTasks(tasks);
        });

        // Floating action button setup
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TaskListActivity.this, TaskActivityAdd.class);
                startActivityForResult(intent, NEW_TASK_ACTIVITY_REQUEST_CODE);
            }
        });

        // Add the functionality to swipe items in the
        // RecyclerView to delete the swiped item.
        ItemTouchHelper helper = new ItemTouchHelper(
                new ItemTouchHelper.SimpleCallback(0,
                        ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                    @Override
                    // We are not implementing onMove() in this app.
                    public boolean onMove(RecyclerView recyclerView,
                                          RecyclerView.ViewHolder viewHolder,
                                          RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    // When the use swipes a task,
                    // delete that task from the database.
                    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                        int position = viewHolder.getAdapterPosition();
                        Task myTask = adapter.getTaskAtPosition(position);
                        Toast.makeText(TaskListActivity.this,
                                getString(R.string.delete_preamble) + " " +
                                        myTask.getName(), Toast.LENGTH_LONG).show();

                        // Delete the word.
                        taskViewModel.deleteTask(myTask);
                    }
                });
        // Attach the item touch helper to the recycler view.
        helper.attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener(new TaskAdapter.ClickListener() {

            @Override
            public void onItemLongClick(View v, int position) {
                Task task = adapter.getTaskAtPosition(position);
                launchEditTaskActivity(task);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    // The options menu has a single item "Clear all data now"
    // that deletes all the entries in the database.
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, as long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        // no inspection Simplifiable If Statement
        if (id == R.id.clear_data) {
            // Add a toast just for confirmation
            Toast.makeText(this, R.string.clear_data_toast_text, Toast.LENGTH_LONG).show();

            // Delete the existing data.
            taskViewModel.deleteAll();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * When the user enters a new task in the TaskActivityAdd,
     * that activity returns the result to this activity.
     * If the user entered a new task, save it in the database.
     *
     * @param requestCode ID for the request
     * @param resultCode  indicates success or failure
     * @param data        The Intent sent back from the TaskActivityAdd,
     *                    which includes the task that the user entered
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_TASK_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            Task task = new Task(data.getStringExtra(TaskActivityAdd.EXTRA_REPLY));
            // Save the data.
            taskViewModel.insert(task);
        } else if (requestCode == UPDATE_TASK_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            String task_data = data.getStringExtra(TaskActivityEdit.EXTRA_REPLY);
            int id = data.getIntExtra(TaskActivityEdit.EXTRA_REPLY_ID, -1);

            if (id != -1) {
                taskViewModel.update(new Task(id, task_data));
            } else {
                Toast.makeText(this, R.string.unable_to_update,
                        Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(
                    this, R.string.empty_not_saved, Toast.LENGTH_LONG).show();
        }
    }

    public void launchEditTaskActivity(Task task) {
        Intent intent = new Intent(this, TaskActivityEdit.class);
        intent.putExtra(EXTRA_DATA_UPDATE_TASK, task.getName());
        intent.putExtra(EXTRA_DATA_ID, task.getId());
        startActivityForResult(intent, UPDATE_TASK_ACTIVITY_REQUEST_CODE);
    }
}
