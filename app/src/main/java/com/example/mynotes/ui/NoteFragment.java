package com.example.mynotes.ui;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.DatePicker;

import com.example.mynotes.data.NoteData;
import com.example.mynotes.utils.Publisher;
import com.example.mynotes.R;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class NoteFragment extends Fragment {

    private static final String ARG_NOTE_DATA = "Param_NoteData";

    private NoteData noteData;      // Данные по карточке
    private Publisher publisher;    // Паблишер, с его помощью обмениваемся данными

    private TextInputEditText title;
    private TextInputEditText description;
    private CheckBox executed;
    private DatePicker datePicker;

    // Для редактирования данных
    public static NoteFragment newInstance(NoteData noteData) {
        NoteFragment fragment = new NoteFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_NOTE_DATA, noteData);
        fragment.setArguments(args);
        return fragment;
    }

    // Для добавления новых данных
    public static NoteFragment newInstance() {
        return new NoteFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            noteData = getArguments().getParcelable(ARG_NOTE_DATA);
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        MainActivity activity = (MainActivity) context;
        publisher = activity.getPublisher();
    }

    @Override
    public void onDetach() {
        publisher = null;
        super.onDetach();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_note, container, false);
        initView(view);
        // если cardData пустая, то это добавление
        if (noteData != null) {
            populateView();
        }
        return view;
    }

    // Здесь соберём данные из views
    @Override
    public void onStop() {
        super.onStop();
        noteData = collectNoteData();
    }

    // Здесь передадим данные в паблишер
    @Override
    public void onDestroy() {
        super.onDestroy();
        publisher.notifySingle(noteData);
    }

    private NoteData collectNoteData() {
        String title = Objects.requireNonNull(this.title.getText()).toString();
        String description = Objects.requireNonNull(this.description.getText()).toString();
        Date date = getDateFromDatePicker();
        boolean executed = this.executed.isChecked();
        if (noteData != null) {
            NoteData answer;
            answer = new NoteData(title, description, executed, date);
            answer.setId(noteData.getId());
            return answer;
        } else {
            return new NoteData(title, description, false, date);
        }
    }

    // Получение даты из DatePicker
    private Date getDateFromDatePicker() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, this.datePicker.getYear());
        cal.set(Calendar.MONTH, this.datePicker.getMonth());
        cal.set(Calendar.DAY_OF_MONTH, this.datePicker.getDayOfMonth());
        return cal.getTime();
    }

    private void initView(View view) {
        title = view.findViewById(R.id.inputTitle);
        description = view.findViewById(R.id.inputDescription);
        datePicker = view.findViewById(R.id.inputDate);
        executed = view.findViewById(R.id.executed);
    }

    private void populateView() {
        title.setText(noteData.getTitle());
        description.setText(noteData.getDescription());
        executed.setChecked(noteData.isExecuted());
        initDatePicker(noteData.getDate());
    }

    // Установка даты в DatePicker
    private void initDatePicker(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        this.datePicker.init(calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH),
                null);
    }
}
