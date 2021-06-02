package com.arthurbritto.methodik.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.arthurbritto.methodik.R;
import com.arthurbritto.methodik.model.Panel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;


public class MainActivity extends AppCompatActivity {

    public static final int NEW_PANEL_ACTIVITY_REQUEST_CODE = 1;
    public static final int UPDATE_PANEL_ACTIVITY_REQUEST_CODE = 2;

    public static final String EXTRA_DATA_UPDATE_PANEL = "extra_word_to_be_updated";
    public static final String EXTRA_DATA_ID = "extra_data_id";
    public static final String EXTRA_REPLY = "com.example.android.Methodik.REPLY";
    public static final String EXTRA_REPLY_ID = "com.android.example.Methodik.REPLY_ID";

    private PanelViewModel panelViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Set up the RecyclerView.
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final PanelAdapter adapter = new PanelAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

            // Set up the WordViewModel.
        panelViewModel = ViewModelProviders.of(this).get(PanelViewModel.class);
        // Get all the words from the database
        // and associate them to the adapter.
        panelViewModel.getAllLists().observe(this, new Observer<List<Panel>>() {
            @Override
            public void onChanged(@Nullable final List<Panel> panels) {
                // Update the cached copy of the panels in the adapter.
                adapter.setPanels(panels);
            }
        });

        // Floating action button setup
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, PanelActivity.class);
                startActivityForResult(intent, NEW_PANEL_ACTIVITY_REQUEST_CODE);
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
                    // When the use swipes a word,
                    // delete that word from the database.
                    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                        int position = viewHolder.getAdapterPosition();
                        Panel myPanel = adapter.getPanelAtPosition(position);
                        Toast.makeText(MainActivity.this,
                                getString(R.string.delete_preamble) + " " +
                                        myPanel.getName(), Toast.LENGTH_LONG).show();

                        // Delete the word.
                        panelViewModel.deletePanel(myPanel);
                    }
                });
        // Attach the item touch helper to the recycler view.
        helper.attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener((v, position) -> {
            Panel panel = adapter.getPanelAtPosition(position);
            launchUpdatePanelActivity(panel);
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.clear_data) {
            // Add a toast just for confirmation
            Toast.makeText(this, R.string.clear_data_toast_text, Toast.LENGTH_LONG).show();

            // Delete the existing data.
            panelViewModel.deleteAll();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * When the user enters a new word in the NewWordActivity,
     * that activity returns the result to this activity.
     * If the user entered a new word, save it in the database.

     * @param requestCode ID for the request
     * @param resultCode indicates success or failure
     * @param data The Intent sent back from the NewWordActivity,
     *             which includes the word that the user entered
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_PANEL_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            Panel panel = new Panel(data.getStringExtra(PanelActivity.EXTRA_REPLY));
            // Save the data.
            panelViewModel.insert(panel);
        } else if (requestCode == UPDATE_PANEL_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            String panel_data = data.getStringExtra(PanelActivity.EXTRA_REPLY);
            int id = data.getIntExtra(PanelActivity.EXTRA_REPLY_ID, -1);

            if (id != -1) {
                panelViewModel.update(new Panel(id, panel_data));
            } else {
                Toast.makeText(this, R.string.unable_to_update,
                        Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(
                    this, R.string.empty_not_saved, Toast.LENGTH_LONG).show();
        }
    }

    public void launchUpdatePanelActivity(Panel panel) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(EXTRA_DATA_UPDATE_PANEL, panel.getName());
        intent.putExtra(EXTRA_DATA_ID, panel.getId());
        startActivityForResult(intent, UPDATE_PANEL_ACTIVITY_REQUEST_CODE);
    }


}
