package com.arthurbritto.methodik.view;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.arthurbritto.methodik.R;

/**
 * Class responsible to edit and add new tasks
 */
public class TaskActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
    }
}