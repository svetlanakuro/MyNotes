package com.example.mynotes;

import android.util.Log;

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
    private List<NoteData> notes = new ArrayList<>();

    @Override
    public NoteSource init(NoteSourceResponse response) {
        collection.get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        notes = new ArrayList<>();
                        for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                            Map<String, Object> doc = document.getData();
                            String id = document.getId();
                            NoteData noteData = NoteDataMapping.toNoteData(id, doc);
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
    public NoteData getNoteData(int position) {
        return notes.get(position);
    }

    @Override
    public void deleteNoteData(int position) {
        collection.document(notes.get(position).getId()).delete();
        notes.remove(position);
    }

    @Override
    public void updateNoteData(int position, NoteData noteData) {
        collection.document(noteData.getId()).set(NoteDataMapping.toDocument(noteData));
        notes.set(position, noteData);
    }

    @Override
    public void addNoteData(NoteData noteData) {
        collection.add(NoteDataMapping.toDocument(noteData)).addOnSuccessListener(documentReference -> noteData.setId(documentReference.getId()));
        notes.add(noteData);
    }

    @Override
    public void clearNoteData() {
        for (NoteData noteData : notes) {
            collection.document(noteData.getId()).delete();
        }
        notes.clear();
    }

    @Override
    public int size() {
        if (notes == null){
            return 0;
        }
        return notes.size();
    }
}
