package com.arthurbritto.methodik.view;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.arthurbritto.methodik.R;

import static com.arthurbritto.methodik.view.MainActivity.EXTRA_DATA_ID;
import static com.arthurbritto.methodik.view.MainActivity.EXTRA_DATA_NAME;

public class TaskActivityAdd extends AppCompatActivity {

    public static final String EXTRA_REPLY = "com.arthurbritto.methodik.REPLY";
    public static final String EXTRA_REPLY_ID = "com.arthurbritto.methodik.REPLY_ID";

    private EditText addTaskView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_add);

        addTaskView = findViewById(R.id.add_panel);
        int id = -1;

        final Bundle extras = getIntent().getExtras();

        // If we are passed content, fill it in for the user to edit.
        if (extras != null) {
            String task = extras.getString(EXTRA_DATA_NAME, "");
            if (!task.isEmpty()) {
                addTaskView.setText(task);
                addTaskView.setSelection(task.length());
                addTaskView.requestFocus();
            }
        } // Otherwise, start with empty fields.

        final Button button = findViewById(R.id.button_save);

        // When the user presses the Save button, create a new Intent for the reply.
        // The reply Intent will be sent back to the calling activity (in this case, TaskListActivity).
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create a new Intent for the reply.
                Intent replyIntent = new Intent();
                if (TextUtils.isEmpty(addTaskView.getText())) {
                    // No task was entered, set the result accordingly.
                    setResult(RESULT_CANCELED, replyIntent);
                } else {
                    // Get the new task that the user entered.
                    String task = addTaskView.getText().toString();
                    // Put the new task in the extras for the reply Intent.
                    replyIntent.putExtra(EXTRA_REPLY, task);
                    if (extras != null && extras.containsKey(EXTRA_DATA_ID)) {
                        int id = extras.getInt(EXTRA_DATA_ID, -1);
                        if (id != -1) {
                            replyIntent.putExtra(EXTRA_REPLY_ID, id);
                        }
                    }
                    // Set the result status to indicate success.
                    setResult(RESULT_OK, replyIntent);
                }
                finish();
            }

        });
    }
}

