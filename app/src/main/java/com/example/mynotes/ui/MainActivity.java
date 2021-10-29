package com.example.mynotes.ui;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.mynotes.data.NoteData;
import com.example.mynotes.data.NoteDataKotlin;
import com.example.mynotes.domain.NoteSource;
import com.example.mynotes.data.NoteSourceFirebaseImpl;
import com.example.mynotes.utils.Publisher;
import com.example.mynotes.R;

import java.util.Calendar;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    private ItemAdapterKotlin adapter;
    private NoteSource noteSource;
    private RecyclerView recyclerView;
    private int currentPosition;

    private Navigation navigation;
    private Publisher publisher = new Publisher();

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navigation = new Navigation(getSupportFragmentManager());

        recyclerView = findViewById(R.id.recyclerView);
        noteSource = new NoteSourceFirebaseImpl();
        adapter = new ItemAdapterKotlin(noteSource);

        noteSource.init(noteSource -> adapter.notifyDataSetChanged());

        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

        int spanCount = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE ? 2 : 1;
        recyclerView.setLayoutManager(new GridLayoutManager(this, spanCount));

        adapter.setListener((view, position) -> {
            currentPosition = position;
            view.showContextMenu(20, 20);
        });

        registerForContextMenu(recyclerView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.notes_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                NoteDataKotlin noteData = new NoteDataKotlin("new title", "new description note", false, Calendar.getInstance().getTime());
                noteData.setId(UUID.randomUUID().toString());
                navigation.addFragment(NoteFragmentKotlin.Companion.newInstance(noteData), true);
                publisher.subscribe(noteData1 -> {
                    noteSource.addNoteData(noteData1);
                    adapter.notifyItemChanged(noteSource.size() - 1);
                    recyclerView.scrollToPosition(noteSource.size() - 1);
                });
                return true;
            case R.id.action_clear:
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Clear notes")
                        .setMessage("Delete all notes?")
                        .setPositiveButton("Yes", (dialog, which) -> {
                            noteSource.clearNoteData();
                            adapter.notifyDataSetChanged();
                        })
                        .setNegativeButton("No", (dialog, which) -> Toast.makeText(MainActivity.this, "Notes not deleted", Toast.LENGTH_SHORT).show())
                        .show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.note_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_update:
                NoteDataKotlin noteData = noteSource.getNoteData(currentPosition);
                ((NoteDataKotlin) noteData).setId(noteSource.getNoteData(currentPosition).getId());
                navigation.addFragment(NoteFragmentKotlin.Companion.newInstance(noteData), true);
                publisher.subscribe(noteData1 -> {
                    noteSource.updateNoteData(currentPosition, noteData1);
                    adapter.notifyItemChanged(currentPosition);
                });
                return true;
            case R.id.action_delete:
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Delete note")
                        .setMessage("Delete this note?")
                        .setPositiveButton("Yes", (dialog, which) -> {
                            noteSource.deleteNoteData(currentPosition);
                            adapter.notifyItemRemoved(currentPosition);
                        })
                        .setNegativeButton("No", (dialog, which) ->
                                Toast.makeText(MainActivity.this, "Note not deleted", Toast.LENGTH_SHORT).show())
                        .show();
                return true;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public Navigation getNavigation() {
        return navigation;
    }

    public Publisher getPublisher() {
        return publisher;
    }
}