package com.example.swabtesttracker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class DetailsActivity extends AppCompatActivity {

    TextView facilitiestxt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        facilitiestxt=findViewById(R.id.marker);

        String title = getIntent().getStringExtra("title");
        facilitiestxt.setText(title);

    }
}