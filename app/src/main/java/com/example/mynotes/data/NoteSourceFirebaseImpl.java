package com.example.mynotes.data;

import android.util.Log;

import com.example.mynotes.domain.NoteSource;
import com.example.mynotes.domain.NoteSourceResponse;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class NoteSourceFirebaseImpl implements NoteSource {

    private static final String COLLECTION = "NOTES";
    private static final String TAG = "NoteSourceFirebaseImpl";

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference collection = db.collection(COLLECTION);
    private List<NoteDataKotlin> notes = new ArrayList<>();

    @Override
    public NoteSource init(NoteSourceResponse response) {
        collection.get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        notes = new ArrayList<>();
                        for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                            Map<String, Object> doc = document.getData();
                            String id = document.getId();
                            NoteDataKotlin noteData = NoteDataMapping.toNoteData(id, doc);
                            notes.add(noteData);
                        }
                        response.initialized(this);
                        Log.d(TAG, "success " + notes.size());
                    } else {
                        Log.d(TAG, "failed ", task.getException());
                    }
                }).addOnFailureListener(e -> Log.d(TAG, "failed", e));

        return this;
    }

    @Override
    public NoteDataKotlin getNoteData(int position) {
        return notes.get(position);
    }

    @Override
    public void deleteNoteData(int position) {
        collection.document(Objects.requireNonNull(notes.get(position).getId())).delete();
        notes.remove(position);
    }

    @Override
    public void updateNoteData(int position, NoteDataKotlin noteData) {
        collection.document(Objects.requireNonNull(noteData.getId())).set(NoteDataMapping.toDocument(noteData));
        notes.set(position, noteData);
    }

    @Override
    public void addNoteData(NoteDataKotlin noteData) {
        collection.add(NoteDataMapping.toDocument(noteData)).addOnSuccessListener(documentReference -> noteData.setId(documentReference.getId()));
        notes.add(noteData);
    }

    @Override
    public void clearNoteData() {
        for (NoteDataKotlin noteData : notes) {
            collection.document(Objects.requireNonNull(noteData.getId())).delete();
        }
        notes.clear();
    }

    @Override
    public int size() {
        if (notes == null) {
            return 0;
        }
        return notes.size();
    }
}
