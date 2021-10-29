package com.example.mynotes.data;

import com.google.firebase.Timestamp;

import java.util.HashMap;
import java.util.Map;

public class NoteDataMapping {

    public static class Fields {
        public final static String DATE = "date";
        public final static String TITLE = "title";
        public final static String DESCRIPTION = "description";
        public final static String EXECUTED = "executed";
    }

    public static NoteDataKotlin toNoteData(String id, Map<String, Object> doc) {
        Timestamp timeStamp = (Timestamp) doc.get(Fields.DATE);
        assert timeStamp != null;
        NoteDataKotlin answer = new NoteDataKotlin((String) doc.get(Fields.TITLE),
                (String) doc.get(Fields.DESCRIPTION),
                (boolean) doc.get(Fields.EXECUTED),
                timeStamp.toDate());
        answer.setId(id);
        return answer;
    }

    public static Map<String, Object> toDocument(NoteDataKotlin noteData) {
        Map<String, Object> answer = new HashMap<>();
        answer.put(Fields.TITLE, noteData.getTitle());
        answer.put(Fields.DESCRIPTION, noteData.getDescription());
        answer.put(Fields.EXECUTED, noteData.isExecuted());
        answer.put(Fields.DATE, noteData.getDate());
        return answer;
    }
}
