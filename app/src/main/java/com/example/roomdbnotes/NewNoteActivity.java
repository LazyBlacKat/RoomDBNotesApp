package com.example.roomdbnotes;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class NewNoteActivity extends AppCompatActivity {

    private EditText noteTitleEdt, noteDescEdt;
    private Button noteBtn;

    public static final String EXTRA_ID = "com.gtappdevelopers.gfgroomdatabase.EXTRA_ID";
    public static final String EXTRA_NOTE_TITLE = "com.gtappdevelopers.gfgroomdatabase.EXTRA_NOTE_TITLE";
    public static final String EXTRA_DESCRIPTION = "com.gtappdevelopers.gfgroomdatabase.EXTRA_COURSE_DESCRIPTION";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_note);


        noteTitleEdt = findViewById(R.id.idEdtNoteTitle);
        noteDescEdt = findViewById(R.id.idEdtNoteDescription);
        noteBtn = findViewById(R.id.idBtn);

        Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_ID)) {
            noteTitleEdt.setText(intent.getStringExtra(EXTRA_NOTE_TITLE));
            noteDescEdt.setText(intent.getStringExtra(EXTRA_DESCRIPTION));
        }
        noteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String noteTitle = noteTitleEdt.getText().toString();
                String noteDesc = noteDescEdt.getText().toString();
                if (noteTitle.isEmpty() || noteDesc.isEmpty()) {
                    Toast.makeText(NewNoteActivity.this, "Can't save empty note or note without a title.", Toast.LENGTH_SHORT).show();
                    return;
                }
                saveNote(noteTitle, noteDesc);
            }
        });
    }

    private void saveNote(String noteTitle, String noteDescription) {
        Intent data = new Intent();

        data.putExtra(EXTRA_NOTE_TITLE, noteTitle);
        data.putExtra(EXTRA_DESCRIPTION, noteDescription);
        int id = getIntent().getIntExtra(EXTRA_ID, -1);
        if (id != -1) {
            data.putExtra(EXTRA_ID, id);
        }

        setResult(RESULT_OK, data);

        Toast.makeText(this, "Note has been saved to Room Database. ", Toast.LENGTH_SHORT).show();
    }
}
