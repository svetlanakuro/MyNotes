package com.example.mynotes;

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
    public void notifySingle(NoteData noteData) {
        for (Observer observer : observers) {
            observer.updateNoteData(noteData);
            unsubscribe(observer);
        }
    }

}
