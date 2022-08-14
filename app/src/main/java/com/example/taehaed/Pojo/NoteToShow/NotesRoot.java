package com.example.taehaed.Pojo.NoteToShow;

import java.io.Serializable;
import java.util.ArrayList;

public class NotesRoot implements Serializable {
    private boolean successful;
    private String message;
    private ArrayList<Note> notes;

    public boolean isSuccessful() {
        return successful;
    }

    public void setSuccessful(boolean successful) {
        this.successful = successful;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<Note> getNotes() {
        return notes;
    }

    public void setNotes(ArrayList<Note> notes) {
        this.notes = notes;
    }
}
