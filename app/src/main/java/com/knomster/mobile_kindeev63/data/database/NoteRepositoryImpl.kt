package com.knomster.mobile_kindeev63.data.database

import androidx.lifecycle.LiveData
import com.knomster.mobile_kindeev63.domain.entities.database.Note
import com.knomster.mobile_kindeev63.domain.interfaces.NoteRepository

class NoteRepositoryImpl(private val noteDao: NoteDao): NoteRepository {
    override suspend fun insertNote(note: Note) {
        noteDao.insertNote(note)
    }

    override fun getAllNotes(): LiveData<List<Note>> {
        return noteDao.getAllNotes()
    }

    override suspend fun deleteNotes(ids: List<Int>) {
        noteDao.deleteNotes(ids)
    }

    override suspend fun getNoteById(id: Int): Note? {
        return noteDao.getNoteById(id)
    }
}