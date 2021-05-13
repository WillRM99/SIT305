package com.example.notesapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.notesapplication.data.DatabaseHelper;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button newNoteButton = findViewById(R.id.newNoteButton);
        Button showNotesButton = findViewById(R.id.showAllNotesButton);

        db = new DatabaseHelper(this);

        newNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Intent newNoteIntent = new Intent(MainActivity.this, NewNoteActivity.class);
                startActivity(newNoteIntent);
            }
        });



        showNotesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Intent showAllNotesIntent = new Intent(MainActivity.this, NotesList.class);
                startActivity(showAllNotesIntent);
            }
        });


    }


}