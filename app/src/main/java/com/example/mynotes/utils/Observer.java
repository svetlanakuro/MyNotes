package com.example.mynotes.utils;

import com.example.mynotes.data.NoteData;

public interface Observer {
    void updateNoteData(NoteData noteData);
}