package com.example.mynotes;

import android.content.Context;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NoteSourceImpl implements NoteSource {

    private List<NoteData> notes;

    public NoteSourceImpl(Context context) {
        notes = new ArrayList<>(Arrays.asList(
                new NoteData(
                        context.getResources().getString(R.string.title1),
                        context.getResources().getString(R.string.description1),
                        false
                ),
                new NoteData(
                        context.getResources().getString(R.string.title2),
                        context.getResources().getString(R.string.description2),
                        false
                ),
                new NoteData(
                        context.getResources().getString(R.string.title3),
                        context.getResources().getString(R.string.description3),
                        false
                ),
                new NoteData(
                        context.getResources().getString(R.string.title4),
                        context.getResources().getString(R.string.description4),
                        false
                ),
                new NoteData(
                        context.getResources().getString(R.string.title5),
                        context.getResources().getString(R.string.description5),
                        false
                ),
                new NoteData(
                        context.getResources().getString(R.string.title6),
                        context.getResources().getString(R.string.description6),
                        false
                )));
    }

    @Override
    public NoteData getNoteData(int position) {
        return notes.get(position);
    }

    @Override
    public void deleteNoteData(int position) {
        notes.remove(position);
    }

    @Override
    public void updateNoteData(int position, NoteData noteData) {
        notes.set(position, noteData);
    }

    @Override
    public void addNoteData(NoteData noteData) {
        notes.add(noteData);
    }

    @Override
    public void clearNoteData() {
        notes.clear();
    }

    @Override
    public int size() {
        return notes.size();
    }
}
