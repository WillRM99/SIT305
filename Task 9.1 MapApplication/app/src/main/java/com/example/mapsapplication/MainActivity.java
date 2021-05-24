package com.example.mapsapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.mapsapplication.data.DatabaseHelper;

public class MainActivity extends AppCompatActivity {


    DatabaseHelper db;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button addLocationButton = findViewById(R.id.AddLocationButton);
        Button showMapButton = findViewById(R.id.ShowMapButton);

        db = new DatabaseHelper(this);

        addLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newNoteIntent = new Intent(MainActivity.this, AddLocationActivity.class);
                finish();
                startActivity(newNoteIntent);
            }
        });


        showMapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent showAllNotesIntent = new Intent(MainActivity.this, MapsActivity.class);

                showAllNotesIntent.putExtra("db", true);


                startActivity(showAllNotesIntent);
            }
        });

    }
}