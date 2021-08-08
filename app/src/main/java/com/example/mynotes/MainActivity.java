package com.example.mynotes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);

        NoteSource noteSource = new NoteSourceImpl(this);

        ItemAdapter adapter = new ItemAdapter(noteSource);

        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

        int spanCount = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE ? 2 : 1;
        recyclerView.setLayoutManager(new GridLayoutManager(this, spanCount));

        adapter.setListener(position ->
                Toast.makeText(this, "Click to " + noteSource.getNoteData(position).getDescription(), Toast.LENGTH_SHORT)
                        .show());
    }
}