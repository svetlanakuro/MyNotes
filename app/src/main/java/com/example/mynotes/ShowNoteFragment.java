package com.example.mynotes;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ShowNoteFragment extends Fragment {

    public static final String ARG_NOTE = "note";
    private Note note;

    public static ShowNoteFragment newInstance(Note note) {
        ShowNoteFragment fragment = new ShowNoteFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_NOTE, note);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            note = getArguments().getParcelable(ARG_NOTE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_show_note, container, false);

        TextView noteDescription = view.findViewById(R.id.noteDescription);
        String[] description = getResources().getStringArray(R.array.description);
        noteDescription.setText(description[note.getNoteIndex()]);

        TextView noteNameView = view.findViewById(R.id.noteName);
        noteNameView.setText(note.getNoteName());
        return view;
    }
}