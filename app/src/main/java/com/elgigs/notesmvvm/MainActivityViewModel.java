package com.elgigs.notesmvvm;

import android.app.Application;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class MainActivityViewModel extends AndroidViewModel {
    private NotesRepository repository;
    private LiveData<List<NotesEntity>> allNotes;

    public MainActivityViewModel(@NonNull Application application) {
        super(application);
        repository = new NotesRepository(application);
        allNotes = repository.getAllNotes();
    }

    public void insert(NotesEntity notesEntity) {
        repository.insert(notesEntity);
    }

    public void update(NotesEntity notesEntity) {
        repository.update(notesEntity);
    }

    public void delete(NotesEntity notesEntity) {
        repository.delete(notesEntity);
    }

    public void deleteAllNotes() {
        repository.deleteAllNotes();
    }

    public LiveData<List<NotesEntity>> getAllNotes() {
        return allNotes;
    }
}
