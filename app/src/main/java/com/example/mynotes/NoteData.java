package com.example.mynotes;

public class NoteData {
    private String title;
    private String description;
    private boolean executed;

    public NoteData(String title, String description, boolean executed) {
        this.title = title;
        this.description = description;
        this.executed = executed;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public boolean isExecuted() {
        return executed;
    }
}
