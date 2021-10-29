package com.example.mynotes.utils;

import com.example.mynotes.data.NoteData;
import com.example.mynotes.data.NoteDataKotlin;

public interface Observer {
    void updateNoteData(NoteDataKotlin noteData);
}