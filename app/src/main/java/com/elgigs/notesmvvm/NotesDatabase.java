package com.elgigs.notesmvvm;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {NotesEntity.class}, version = 1, exportSchema = false)
public abstract class NotesDatabase extends RoomDatabase {

    private static NotesDatabase instance;

    public abstract NotesDao notesDao();

    public static synchronized NotesDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    NotesDatabase.class, "notes_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PolulateDBAsyncTask(instance).execute();
        }
    };

    private static class PolulateDBAsyncTask extends AsyncTask<Void, Void, Void> {
        private NotesDao notesDao;
        private PolulateDBAsyncTask(NotesDatabase db) {
            notesDao = db.notesDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            notesDao.insert(new NotesEntity("Title 1", "Description 1", 1));
            notesDao.insert(new NotesEntity("Title 2", "Description 2", 2));
            notesDao.insert(new NotesEntity("Title 3", "Description 3", 3));
            return null;
        }
    }

}