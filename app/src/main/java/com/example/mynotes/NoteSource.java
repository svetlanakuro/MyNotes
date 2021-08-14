package com.example.mynotes;

public interface NoteSource {

    NoteSource init(NoteSourceResponse response);

    NoteData getNoteData(int position);

    void deleteNoteData(int position);

    void updateNoteData(int position, NoteData noteData);

    void addNoteData(NoteData noteData);

    void clearNoteData();

    int size();

}
