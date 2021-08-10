package com.example.mynotes;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
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

public class MainActivity extends AppCompatActivity {

    private ItemAdapter adapter;
    private NoteSource noteSource;
    private RecyclerView recyclerView;
    private int currentPosition;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);

        noteSource = new NoteSourceImpl(this);

        adapter = new ItemAdapter(noteSource);

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
                noteSource.addNoteData(new NoteData("new title", "new description note", false));
                adapter.notifyItemChanged(noteSource.size() - 1);
                recyclerView.scrollToPosition(noteSource.size() - 1);
                return true;
            case R.id.action_clear:
                noteSource.clearNoteData();
                adapter.notifyDataSetChanged();
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
                noteSource.updateNoteData(currentPosition, new NoteData("new title", "new description note", false));
                adapter.notifyItemChanged(currentPosition);
                return true;
            case R.id.action_delete:
                noteSource.deleteNoteData(currentPosition);
                adapter.notifyItemRemoved(currentPosition);
                return true;
        }
        return super.onContextItemSelected(item);
    }
}