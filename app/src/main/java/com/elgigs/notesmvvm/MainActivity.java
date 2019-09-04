package com.elgigs.notesmvvm;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import static com.elgigs.notesmvvm.EditNotesAcitvity.EXTRA_ID;
import static com.elgigs.notesmvvm.EditNotesAcitvity.EXTRA_TITLE;

public class MainActivity extends AppCompatActivity {

    private static final int ADD_NOTE = 1;
    public static final int EDIT_NOTE = 2;
    private MainActivityViewModel mainActivityViewModel;
    private RecyclerView recyclerView;
    private FloatingActionButton addNoteButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.notes_recycler_view);
        addNoteButton = findViewById(R.id.add_note_fab);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
//        notesAdapter = new NotesAdapter();
        final NotesAdapter notesAdapter = new NotesAdapter();
        recyclerView.setAdapter(notesAdapter);

        notesAdapter.setItemClickListener(new NotesAdapter.ItemClickListener() {
            @Override
            public void onItemClick(NotesEntity notesEntity) {
                Intent intent = new Intent(MainActivity.this, EditNotesAcitvity.class);
                intent.putExtra(EXTRA_ID, notesEntity.getId());
                intent.putExtra(EXTRA_TITLE, notesEntity.getTitle());
                intent.putExtra(EditNotesAcitvity.EXTRA_DESCRIPTION, notesEntity.getDescription());
                intent.putExtra(EditNotesAcitvity.EXTRA_PRIORITY, notesEntity.getPriority());
                startActivityForResult(intent, EDIT_NOTE);
            }
        });

        addNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, EditNotesAcitvity.class);
                startActivityForResult(intent, ADD_NOTE);
            }
        });

        mainActivityViewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);
        mainActivityViewModel.getAllNotes().observe(this, new Observer<List<NotesEntity>>() {
            @Override
            public void onChanged(List<NotesEntity> notesEntities) {

                notesAdapter.submitList(notesEntities);

            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                mainActivityViewModel.delete(notesAdapter.getNoteAt(viewHolder.getAdapterPosition()));
                Toast.makeText(MainActivity.this, "Note deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

    }

     

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_NOTE && resultCode == RESULT_OK) {
            String title = data.getStringExtra(EXTRA_TITLE);
            String description = data.getStringExtra(EditNotesAcitvity.EXTRA_DESCRIPTION);
            int priority = data.getIntExtra(EditNotesAcitvity.EXTRA_PRIORITY, 1);

            NotesEntity notesEntity = new NotesEntity(title, description, priority);
            mainActivityViewModel.insert(notesEntity);
        } else if (requestCode == EDIT_NOTE && resultCode == RESULT_OK) {

            int id = data.getIntExtra(EXTRA_ID, -1);

            if (id == -1) {
                Toast.makeText(this, "Note can't be updated", Toast.LENGTH_SHORT).show();
                return;
            }


            String title = data.getStringExtra(EXTRA_TITLE);
            String description = data.getStringExtra(EditNotesAcitvity.EXTRA_DESCRIPTION);
            int priority = data.getIntExtra(EditNotesAcitvity.EXTRA_PRIORITY, 1);

            NotesEntity notesEntity = new NotesEntity(title, description, priority);
            notesEntity.setId(id);

            mainActivityViewModel.update(notesEntity);

        }
        else {
            Toast.makeText(this, "Note not saved", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.mainactivity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_all_notes:
                mainActivityViewModel.deleteAllNotes();
                Toast.makeText(this, "All Notes Deleted", Toast.LENGTH_SHORT).show();
                return true;
                default:
                    return super.onOptionsItemSelected(item);
        }
    }
}
