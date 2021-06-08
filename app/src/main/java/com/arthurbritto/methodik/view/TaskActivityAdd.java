package com.arthurbritto.methodik.view;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.arthurbritto.methodik.R;

public class TaskActivityAdd extends AppCompatActivity {

    public static final String EXTRA_REPLY = "com.arthurbritto.methodik.REPLY" ;
    public static final String EXTRA_REPLY_ID = "com.arthurbritto.methodik.REPLY_ID" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_add);
    }
}