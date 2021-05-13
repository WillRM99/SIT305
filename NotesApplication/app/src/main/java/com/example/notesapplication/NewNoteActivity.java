package com.example.notesapplication;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.notesapplication.data.DatabaseHelper;
import com.example.notesapplication.model.Note;

public class NewNoteActivity extends AppCompatActivity {

    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_note_layout);


        EditText noteText = findViewById(R.id.newNoteEditText);
        Button saveButton = findViewById((R.id.saveButton));
        db = new DatabaseHelper(this);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String noteContent = noteText.getText().toString();

                if(noteContent.equals(null) || noteContent.equals(""))
                {
                    Toast.makeText(NewNoteActivity.this, "Error! Add text to save note", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Note note = new Note(noteContent);
                    long result = db.insertNote(note);
                    if(result > 0)
                    {
                        Toast.makeText(NewNoteActivity.this, "Note Saved Successfully", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(NewNoteActivity.this, "Error! Note NOT Saved", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
    }
}