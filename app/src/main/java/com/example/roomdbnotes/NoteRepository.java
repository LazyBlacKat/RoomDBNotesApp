package com.example.roomdbnotes;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class NoteRepository {

    private Dao dao;
    private LiveData<List<NoteModal>> allNote;

    public NoteRepository(Application application) {
        NoteDatabase database = NoteDatabase.getInstance(application);
        dao = database.Dao();
        allNote = dao.getAllNote();
    }

    public void insert(NoteModal model) {
        new InsertNoteAsyncTask(dao).execute(model);
    }

    public void update(NoteModal model) {
        new UpdateNoteAsyncTask(dao).execute(model);
    }

    public void delete(NoteModal model) {
        new DeleteNoteAsyncTask(dao).execute(model);
    }

    public void deleteAllNote() {
        new DeleteAllNoteAsyncTask(dao).execute();
    }

    public LiveData<List<NoteModal>> getAllNote() {
        return allNote;
    }

    private static class InsertNoteAsyncTask extends AsyncTask<NoteModal, Void, Void> {
        private Dao dao;

        private InsertNoteAsyncTask(Dao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(NoteModal... model) {
            dao.insert(model[0]);
            return null;
        }
    }

    private static class UpdateNoteAsyncTask extends AsyncTask<NoteModal, Void, Void> {
        private Dao dao;

        private UpdateNoteAsyncTask(Dao dao) { this.dao = dao; }

        @Override
        protected Void doInBackground(NoteModal... models) {
            dao.update(models[0]);
            return null;
        }
    }

    private static class DeleteNoteAsyncTask extends AsyncTask<NoteModal, Void, Void> {
        private Dao dao;

        private DeleteNoteAsyncTask(Dao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(NoteModal... models) {
            dao.delete(models[0]);
            return null;
        }
    }

    private static class DeleteAllNoteAsyncTask extends AsyncTask<Void, Void, Void> {
        private Dao dao;
        private DeleteAllNoteAsyncTask(Dao dao) {
            this.dao = dao;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            dao.deleteAllNote();
            return null;
        }
    }
}
