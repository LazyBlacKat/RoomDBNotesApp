package com.example.roomdbnotes;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class ViewModal extends AndroidViewModel {

    private NoteRepository repository;

    private LiveData<List<NoteModal>> allNote;

    public ViewModal(@NonNull Application application) {
        super(application);
        repository = new NoteRepository(application);
        allNote = repository.getAllNote();
    }

    public void insert(NoteModal model) {
        repository.insert(model);
    }

    public void update(NoteModal model) {
        repository.update(model);
    }

    public void delete(NoteModal model) {
        repository.delete(model);
    }

    public void deleteAllNote() {
        repository.deleteAllNote();
    }

    public LiveData<List<NoteModal>> getAllNote() {
        return allNote;
    }
}

