package com.example.xodatabase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;

public class History extends AppCompatActivity {

    TextView history;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        Intent previousIntent = getIntent();
        String[] results = previousIntent.getStringArrayExtra("results");


        history = (TextView) findViewById(R.id.history_TV);
        if (results != null) {
            StringBuilder show = new StringBuilder();
            for (int i = 0; i < results.length; i++) {
                show.append(results[i]).append('\n');
            }
            history.setText(show);
        }
        else
            history.setText("");

    }
}