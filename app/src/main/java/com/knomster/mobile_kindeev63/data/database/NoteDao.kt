package com.knomster.mobile_kindeev63.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.knomster.mobile_kindeev63.domain.entities.database.Note

@Dao
interface NoteDao {
    @Insert(Note::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: Note)

    @Query("SELECT * FROM table_notes")
    fun getAllNotes(): LiveData<List<Note>>

    @Query("DELETE FROM table_notes WHERE id IN (:ids)")
    suspend fun deleteNotes(ids: List<Int>)

    @Query("SELECT * FROM table_notes WHERE id = :id")
    suspend fun getNoteById(id: Int): Note?
}