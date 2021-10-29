package com.example.mynotes.domain;

import com.example.mynotes.data.NoteData;
import com.example.mynotes.data.NoteDataKotlin;

public interface NoteSource {

    NoteSource init(NoteSourceResponse response);

    NoteDataKotlin getNoteData(int position);

    void deleteNoteData(int position);

    void updateNoteData(int position, NoteDataKotlin noteData);

    void addNoteData(NoteDataKotlin noteData);

    void clearNoteData();

    int size();

}
