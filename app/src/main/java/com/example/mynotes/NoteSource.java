package com.example.mynotes;

public interface NoteSource {
    NoteData getNoteData(int position);
    int size();
}
