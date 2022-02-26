package com.another.notasapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.another.notasapp.R;
import com.another.notasapp.adapters.FolderNotesAdapter;
import com.another.notasapp.adapters.NotesAdapter;
import com.another.notasapp.models.entity.Notes;

import java.util.ArrayList;
import java.util.List;

public class NotesActivity extends AppCompatActivity {
    private RecyclerView rView;
    private NotesAdapter rAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_notas2);

        rView = findViewById(R.id.recyclerViewNotas);
        Bundle bundle = getIntent().getExtras();
        int noteId = bundle.getInt("noteId", 0);
        Toast.makeText(this, String.valueOf(noteId), Toast.LENGTH_SHORT).show();
        List<Notes> dummyData = new ArrayList<>();
        Notes Notes = new Notes();
        Notes Notes2 = new Notes();
        Notes.setName("Nuevo nota");
        Notes2.setName("Otro nota");
        dummyData.add(Notes);
        dummyData.add(Notes2);

        // Se configura el recycler view
        RecyclerView.Adapter adapterNotes = new NotesAdapter(dummyData);
        rView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rView.setAdapter(adapterNotes);

    }
}