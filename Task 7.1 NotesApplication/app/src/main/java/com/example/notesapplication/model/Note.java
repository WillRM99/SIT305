package com.example.notesapplication.model;

public class Note {
    private int noteID;
    private String noteContents;

    public Note(int noteID, String noteContents)
    {
        this.noteID = noteID;
        this.noteContents = noteContents;
    }

    public Note(String noteContents)
    {
        this.noteContents = noteContents;
    }
    public Note(int noteID)
    {
        this.noteID = noteID;
    }

    public Note() {
    }

    public int getNoteID() {
        return noteID;
    }


    public String getNoteContents() {
        return noteContents;
    }

    public void setNoteID(int noteID) {
        this.noteID = noteID;
    }


    public void setNoteContents(String noteContents) {
        this.noteContents = noteContents;
    }
}
