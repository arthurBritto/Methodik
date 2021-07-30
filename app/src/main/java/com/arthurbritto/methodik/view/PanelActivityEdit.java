package com.arthurbritto.methodik.view;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.arthurbritto.methodik.R;

import static com.arthurbritto.methodik.view.MainActivity.DEFAULT_ID;
import static com.arthurbritto.methodik.view.MainActivity.EXTRA_PANEL_ID;
import static com.arthurbritto.methodik.view.MainActivity.EXTRA_PANEL_NAME;
import static com.arthurbritto.methodik.view.MainActivity.EXTRA_COLOR;

/**
 * This class displays a screen where the user can edit a Panel.
 * The PanelActivityEdit returns the entered Panel to the calling activity
 * (MainActivity), which then stores the new Panel and updates the list.
 */
public class PanelActivityEdit extends AppCompatActivity {

    public static final String EXTRA_REPLY_PANEL_EDITED = "extra_reply_panel_edited";
    public static final String EXTRA_REPLY_PANEL_ID = "extra_reply_panel_id";
    public static final String EXTRA_REPLY_VIEW_COLOR = "extra_reply_view_color";
    
    private EditText editPanelView;
    private View editColorView;
    private int viewColor;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_panel_edit);

        editPanelView = findViewById(R.id.edit_panel_text);
        editColorView = findViewById(R.id.view_panel_color_change);

        final Bundle extras = getIntent().getExtras();

        // If we are passed content, fill it in for the user to edit.
        if (extras != null) {
            String panelName = extras.getString(EXTRA_PANEL_NAME, "");
            int buttonColor = extras.getInt(EXTRA_COLOR, DEFAULT_ID);
            if (!panelName.isEmpty()) {
                editPanelView.setText(panelName);
                editPanelView.setSelection(panelName.length());
                editPanelView.requestFocus();
            }
        } // Otherwise, start with empty fields.

        final Button button = findViewById(R.id.button_save);

        // When the user presses the Save button, create a new Intent for the reply.
        // The reply Intent will be sent back to the calling activity (in this case, MainActivity).
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create a new Intent for the reply.
                Intent replyIntent = new Intent();
                if (TextUtils.isEmpty(editPanelView.getText())) {
                    // No panel was entered, set the result accordingly.
                    setResult(RESULT_CANCELED, replyIntent);
                } else {
                    // Get the new panelName, color and panelId that the user entered.
                    String panelName = editPanelView.getText().toString();
                    int panelId = extras.getInt(EXTRA_PANEL_ID, DEFAULT_ID);
                    // Put the new panelName in the extras for the reply Intent.
                    replyIntent.putExtra(EXTRA_REPLY_PANEL_EDITED, panelName);
                    replyIntent.putExtra(EXTRA_REPLY_VIEW_COLOR, viewColor);
                    replyIntent.putExtra(EXTRA_REPLY_PANEL_ID, panelId);
                    // Set the result status to indicate success.
                    setResult(RESULT_OK, replyIntent);
                }
                finish();
            }
        });
    }

    /**
     * Handles the onClick for the background color buttons. Gets the background
     * color of the button that was clicked, and sets the button on the
     * Edit text to that color.
     */
    public void changeBackground(View view) {
        Drawable color = view.getBackground();
        editColorView.setBackground(color);
        viewColor = Integer.parseInt((String) view.getTag());
    }
}

