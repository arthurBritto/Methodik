package com.arthurbritto.methodik.view;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.arthurbritto.methodik.R;

/**
 * This class displays a screen where the user add a new Panel.
 * The PanelActivityAdd returns the entered Panel to the calling activity
 * (MainActivity), which then stores the new Panel and updates the list.
 */
public class  PanelActivityAdd extends AppCompatActivity {

    public static final String EXTRA_REPLY = "com.example.android.Methodik.REPLY";
    public static final String EXTRA_REPLY_ID = "com.android.example.Methodik.REPLY_ID";
    public static final String EXTRA_DATA_UPDATE_PANEL = "extra_panel_to_be_updated";
    public static final String EXTRA_DATA_ID = "extra_data_id";

    private EditText addPanelView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_panel_add);

        addPanelView = findViewById(R.id.add_panel);

        final Bundle extras = getIntent().getExtras();

        final Button button = findViewById(R.id.button_save);
        // When the user presses the Save button, create a new Intent for the reply.
        // The reply Intent will be sent back to the calling activity (in this case, MainActivity).
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create a new Intent for the reply.
                Intent replyIntent = new Intent();
                if (TextUtils.isEmpty(addPanelView.getText())) {
                    // No panel was entered, set the result accordingly.
                    setResult(RESULT_CANCELED, replyIntent);
                } else {
                    // Get the new panel that the user entered.
                    String panel = addPanelView.getText().toString();
                    // Put the new panel in the extras for the reply Intent.
                    replyIntent.putExtra(EXTRA_REPLY, panel);
                    // Set the result status to indicate success.
                    setResult(RESULT_OK, replyIntent);
                }
                finish();
            }
        });
    }
}
