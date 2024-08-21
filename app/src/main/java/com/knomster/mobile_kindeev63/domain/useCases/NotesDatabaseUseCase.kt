package com.knomster.mobile_kindeev63.domain.useCases

import androidx.lifecycle.LiveData
import com.knomster.mobile_kindeev63.domain.entities.database.Note
import com.knomster.mobile_kindeev63.domain.interfaces.NoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class NotesDatabaseUseCase(private val noteRepository: NoteRepository) {

    suspend fun insertNote(note: Note) {
        withContext(Dispatchers.IO) {
            noteRepository.insertNote(note)
        }
    }

    fun getAllNotes(): LiveData<List<Note>> {
        return noteRepository.getAllNotes()
    }

    suspend fun deleteNotes(ids: List<Int>) {
        withContext(Dispatchers.IO) {
            noteRepository.deleteNotes(ids)
        }
    }

    suspend fun getNoteById(id: Int): Note? {
        return noteRepository.getNoteById(id)
    }
}