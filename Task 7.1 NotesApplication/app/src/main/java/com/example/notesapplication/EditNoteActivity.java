package com.example.notesapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.notesapplication.data.DatabaseHelper;
import com.example.notesapplication.model.Note;

public class EditNoteActivity extends AppCompatActivity {
    String noteContent;
    DatabaseHelper db;
    int noteID;
    String stringID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);

        db = new DatabaseHelper(this);

        EditText noteText = findViewById(R.id.editNoteTextView);
        Button saveButton = findViewById(R.id.saveButton);
        Button deleteButton = findViewById(R.id.deleteButton);

        Intent intent = getIntent();
        noteText.setText(intent.getStringExtra("note"));
        stringID = getIntent().getStringExtra("noteID");

        noteID = Integer.parseInt(stringID);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){

                noteContent = noteText.getText().toString();

                if(noteContent.equals(null) || noteContent.equals(""))
                {
                    Toast.makeText(EditNoteActivity.this, "Error! Add text to save note", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    int updateRow = db.saveNote(new Note(noteID, noteContent));

                    if(updateRow > 0)
                    {
                        Toast.makeText(EditNoteActivity.this, "Note Update Saved Successfully", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(EditNoteActivity.this, NotesList.class);

                        finish();

                        startActivityForResult(intent, 1);
                    }
                    else
                    {
                        Toast.makeText(EditNoteActivity.this, "Error! Note NOT Saved", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });



        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){

                int deleteRow = db.deleteNote(new Note(noteID, noteContent));

                if(deleteRow > 0)
                {
                    Toast.makeText(EditNoteActivity.this, "Note Deleted Successfully", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(EditNoteActivity.this, NotesList.class);

                    finish();

                    startActivityForResult(intent, 1);
                }
                else
                {
                    Toast.makeText(EditNoteActivity.this, "Error! Note NOT Deleted", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}