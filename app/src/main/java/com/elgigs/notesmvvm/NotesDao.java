package com.elgigs.notesmvvm;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface NotesDao {

    @Insert
    void insert(NotesEntity notesEntity);

    @Update
    void update(NotesEntity notesEntity);

    @Delete
    void delete(NotesEntity notesEntity);

    @Query("DELETE FROM notes_table")
    void deleteAllNotes();

    @Query("SELECT * FROM notes_table ORDER BY priority DESC")
    LiveData<List<NotesEntity>> getAllNotes();
}
