package com.knomster.mobile_kindeev63.domain.interfaces

import androidx.lifecycle.LiveData
import com.knomster.mobile_kindeev63.domain.entities.database.Note

interface NoteRepository {

    suspend fun insertNote(note: Note)

    fun getAllNotes(): LiveData<List<Note>>

    suspend fun deleteNotes(ids: List<Int>)

    suspend fun getNoteById(id: Int): Note?
}