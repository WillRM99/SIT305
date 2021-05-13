package com.example.notesapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.notesapplication.data.DatabaseHelper;
import com.example.notesapplication.model.Note;

import java.util.ArrayList;
import java.util.List;

public class NotesList extends AppCompatActivity {
    List<Note> noteList;

    ArrayList<String> noteArrayList;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_list);

        ListView noteListView = findViewById(R.id.notesListView);
        noteArrayList = new ArrayList<>();
        List<Integer> noteID = new ArrayList<>();


        DatabaseHelper db = new DatabaseHelper(NotesList.this);

        noteList = db.fetchAllNotes();

        for (Note note : noteList) {
            noteArrayList.add(note.getNoteContents());
            noteID.add(note.getNoteID());

        }

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, noteArrayList);
        noteListView.setAdapter(adapter);

        noteListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(NotesList.this, EditNoteActivity.class);
                intent.putExtra("note", noteArrayList.get(position));
                intent.putExtra("noteID", noteID.get(position)+"");
                finish();
                startActivityForResult(intent, 1);

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (resultCode == RESULT_OK)
        {
            if(requestCode == 1)
            {
                Toast.makeText(this, "result OK", Toast.LENGTH_LONG).show();
            }
        }
        else if (resultCode == RESULT_CANCELED)
        {
            Toast.makeText(this, "please enter your expense", Toast.LENGTH_LONG).show();
        }

    }

}
