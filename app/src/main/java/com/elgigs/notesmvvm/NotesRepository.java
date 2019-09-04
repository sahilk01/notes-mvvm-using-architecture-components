package com.elgigs.notesmvvm;

import android.app.Application;
import android.os.AsyncTask;

import java.util.List;

import androidx.lifecycle.LiveData;

public class NotesRepository {

    private NotesDao notesDao;
//    private NotesDatabase notesDatabase;
    private LiveData<List<NotesEntity>> allNotes;

    public NotesRepository(Application application) {
        NotesDatabase database = NotesDatabase.getInstance(application);
        notesDao = database.notesDao();
        allNotes = notesDao.getAllNotes();
    }

    public void insert(NotesEntity notesEntity) {
        new InsertNoteAsynTask(notesDao).execute(notesEntity);
    }

    public void update(NotesEntity notesEntity) {

        new UpdateNotesAsyncTask(notesDao).execute(notesEntity);

    }

    public void delete(NotesEntity notesEntity) {
        new DeleteNotesAsyncTask(notesDao).execute(notesEntity);
    }

    public void deleteAllNotes() {
        new DeleteAllTheNotes(notesDao).execute();
    }

    public LiveData<List<NotesEntity>> getAllNotes() {
        return allNotes;
    }

    private static class InsertNoteAsynTask extends AsyncTask<NotesEntity, Void, Void> {

        private NotesDao notesDao;
        private InsertNoteAsynTask(NotesDao notesDao) {
            this.notesDao = notesDao;
        }

        @Override
        protected Void doInBackground(NotesEntity... notesEntities) {
            notesDao.insert(notesEntities[0]);
            return null;
        }
    }

    private static class UpdateNotesAsyncTask extends AsyncTask<NotesEntity, Void, Void> {

        private NotesDao notesDao;
        private UpdateNotesAsyncTask(NotesDao notesDao) {

            this.notesDao = notesDao;

        }

        @Override
        protected Void doInBackground(NotesEntity... notesEntities) {

            notesDao.update(notesEntities[0]);
            return null;
        }
    }

    private static class DeleteNotesAsyncTask extends AsyncTask<NotesEntity, Void, Void> {

        private NotesDao notesDao;

        private DeleteNotesAsyncTask(NotesDao notesDao){
            this.notesDao = notesDao;
        }

        @Override
        protected Void doInBackground(NotesEntity... notesEntities) {
            notesDao.delete(notesEntities[0]);
            return null;
        }
    }

    private static class DeleteAllTheNotes extends AsyncTask<Void, Void, Void> {

        private NotesDao notesDao;
        private DeleteAllTheNotes(NotesDao notesDao) {
            this.notesDao = notesDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            notesDao.deleteAllNotes();
            return null;
        }
    }
}
