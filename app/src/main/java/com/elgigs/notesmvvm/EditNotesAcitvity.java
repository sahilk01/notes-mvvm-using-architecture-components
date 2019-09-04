package com.elgigs.notesmvvm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

public class EditNotesAcitvity extends AppCompatActivity {
    public static final String EXTRA_ID = "com.elgigs.notesmvvm.EXTRA_ID";
    public static final String EXTRA_TITLE = "com.elgigs.notesmvvm.EXTRA_TITLE";
    public static final String EXTRA_DESCRIPTION = "com.elgigs.notesmvvm.EXTRA_DESCRIPTION";
    public static final String EXTRA_PRIORITY = "com.elgigs.notesmvvm.EXTRA_PRIORITY";
    private EditText noteTitle, noteDescription;
    private NumberPicker notePriority;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_notes_acitvity);
        noteTitle = findViewById(R.id.et_title);
        noteDescription = findViewById(R.id.et_description);
        notePriority = findViewById(R.id.priority_picker);

        notePriority.setMinValue(1);
        notePriority.setMaxValue(10);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

        Intent intent = getIntent();


        if (intent.hasExtra(EXTRA_ID)){
            setTitle("Edit Note");
            noteTitle.setText(intent.getStringExtra(EXTRA_TITLE));
            noteDescription.setText(intent.getStringExtra(EXTRA_DESCRIPTION));
            notePriority.setValue(intent.getIntExtra(EXTRA_PRIORITY, 1));
        } else {
            setTitle("Add Note");
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.action_bar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_note:
                saveNote();
                return true;
                default:
                    return super.onOptionsItemSelected(item);

        }
    }

    private void saveNote() {
        String title = noteTitle.getText().toString();
        String description = noteDescription.getText().toString();
        int priority = notePriority.getValue();

                if (title.trim().isEmpty() || description.trim().isEmpty()) {
            Toast.makeText(this, "Note is empty", Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent();
            intent.putExtra(EXTRA_TITLE, title);
            intent.putExtra(EXTRA_DESCRIPTION, description);
            intent.putExtra(EXTRA_PRIORITY, priority);

            int id = getIntent().getIntExtra(EXTRA_ID, -1);

            if (id != -1) {
                intent.putExtra(EXTRA_ID, id);
            }

            setResult(RESULT_OK, intent);
            finish();
        }
    }
}
