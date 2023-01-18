package com.example.roomdbnotes;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.HashSet;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView noteRV;
    private static final int ADD_NOTE_REQUEST = 1;
    private static final int EDIT_NOTE_REQUEST = 2;
    private ViewModal viewmodal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        noteRV = findViewById(R.id.idRVNote);
        FloatingActionButton fab = findViewById(R.id.idFABAdd);
        FloatingActionButton faq = findViewById(R.id.idFAQ);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NewNoteActivity.class);
                startActivityForResult(intent, ADD_NOTE_REQUEST);
            }
        });

        faq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(MainActivity.this)
                        .setIcon(android.R.drawable.ic_dialog_info)
                        .setTitle("How to use the notes app?")
                        .setMessage("1. Make new notes with the + icon \n 2. Delete notes by swiping them left or right \n 3. Check and edit notes after clicking on them")
                        .setNegativeButton("Ok", null).show();
            }
        });

        noteRV.setLayoutManager(new LinearLayoutManager(this));
        noteRV.setHasFixedSize(true);

        final NoteRVAdapter adapter = new NoteRVAdapter();

        noteRV.setAdapter(adapter);

        viewmodal = ViewModelProviders.of(this).get(ViewModal.class);

        viewmodal.getAllNote().observe(this, new Observer<List<NoteModal>>() {
            @Override
            public void onChanged(List<NoteModal> models) {
                adapter.submitList(models);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                viewmodal.delete(adapter.getNoteAt(viewHolder.getAdapterPosition()));
                Toast.makeText(MainActivity.this, "Note deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(noteRV);
        adapter.setOnItemClickListener(new NoteRVAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(NoteModal model) {
                Intent intent = new Intent(MainActivity.this, NewNoteActivity.class);
                intent.putExtra(NewNoteActivity.EXTRA_ID, model.getId());
                intent.putExtra(NewNoteActivity.EXTRA_NOTE_TITLE, model.getNoteTitle());
                intent.putExtra(NewNoteActivity.EXTRA_DESCRIPTION, model.getNoteDescription());

                startActivityForResult(intent, EDIT_NOTE_REQUEST);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_NOTE_REQUEST && resultCode == RESULT_OK) {
            String noteTitle = data.getStringExtra(NewNoteActivity.EXTRA_NOTE_TITLE);
            String noteDescription = data.getStringExtra(NewNoteActivity.EXTRA_DESCRIPTION);
            NoteModal model = new NoteModal(noteTitle, noteDescription);
            viewmodal.insert(model);
            Toast.makeText(this, "Note saved", Toast.LENGTH_SHORT).show();
        } else if (requestCode == EDIT_NOTE_REQUEST && resultCode == RESULT_OK) {
            int id = data.getIntExtra(NewNoteActivity.EXTRA_ID, -1);
            if (id == -1) {
                Toast.makeText(this, "Note can't be updated", Toast.LENGTH_SHORT).show();
                return;
            }
            String noteTitle = data.getStringExtra(NewNoteActivity.EXTRA_NOTE_TITLE);
            String noteDesc = data.getStringExtra(NewNoteActivity.EXTRA_DESCRIPTION);
            NoteModal model = new NoteModal(noteTitle, noteDesc);
            model.setId(id);
            viewmodal.update(model);
            Toast.makeText(this, "Note updated", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Note was not saved", Toast.LENGTH_SHORT).show();
        }
    }
}
