package com.example.roomdbnotes;

import androidx.lifecycle.LiveData;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@androidx.room.Dao
public interface Dao {

    @Insert
    void insert(NoteModal model);

    @Update
    void update(NoteModal model);

    @Delete
    void delete(NoteModal model);

    @Query("DELETE FROM note_table")
    void deleteAllNote();

    @Query("SELECT * FROM note_table ORDER BY id DESC")
    LiveData<List<NoteModal>> getAllNote();
}

