package com.example.mynotes.utils;

import com.example.mynotes.data.NoteData;
import com.example.mynotes.data.NoteDataKotlin;

import java.util.ArrayList;
import java.util.List;

public class Publisher {
    private List<Observer> observers;   // Все обозреватели

    public Publisher() {
        observers = new ArrayList<>();
    }

    // Подписать
    public void subscribe(Observer observer) {
        observers.add(observer);
    }

    // Отписать
    public void unsubscribe(Observer observer) {
        observers.remove(observer);
    }

    // Разослать событие
    public void notifySingle(NoteDataKotlin noteData) {
        for (Observer observer : observers) {
            observer.updateNoteData(noteData);
            unsubscribe(observer);
        }
    }

}
