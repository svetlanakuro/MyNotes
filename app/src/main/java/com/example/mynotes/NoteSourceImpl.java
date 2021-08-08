package com.example.mynotes;

import android.content.Context;

public class NoteSourceImpl implements NoteSource {

    private Context context;
    private NoteData[] notes;

    public NoteSourceImpl(Context context) {
        this.context = context;
        notes = new NoteData[]{
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
                )
        };
    }

    @Override
    public NoteData getNoteData(int position) {
        return notes[position];
    }

    @Override
    public int size() {
        return notes.length;
    }
}
