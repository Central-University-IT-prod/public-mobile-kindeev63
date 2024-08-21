package com.knomster.mobile_kindeev63.presentation.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.knomster.mobile_kindeev63.domain.entities.database.Note
import com.knomster.mobile_kindeev63.presentation.entities.NoteWithSelected
import kotlinx.coroutines.launch

class NotesListScreenViewModel(val mainViewModel: MainViewModel) : ViewModel() {
    private val _notes = MutableLiveData<List<NoteWithSelected>>(emptyList())
    val notes: LiveData<List<NoteWithSelected>> = _notes
    private val _searchText = MutableLiveData<String?>(null)
    val searchText: LiveData<String?> = _searchText

    fun search(text: String?) {
        _searchText.postValue(text)
    }

    fun setAllNotes(allNotes: List<Note>) {
        val notesWithSelected = mutableListOf<NoteWithSelected>()
        allNotes.forEach { note ->
            val selected = notes.value?.find { it.note.id == note.id }?.selected ?: false
            notesWithSelected.add(
                NoteWithSelected(
                    note = note,
                    selected = selected
                )
            )
        }
        _notes.postValue(notesWithSelected.sortedBy { it.note.id })
    }

    fun changeSelectionStateOfNote(id: Int) {
        val note = notes.value?.find { it.note.id == id } ?: return
        val newNotes = notes.value?.toMutableList().apply {
            this?.remove(note)
            this?.add(note.copy(selected = note.selected.not()))
        } ?: return
        _notes.postValue(newNotes.sortedBy { it.note.id })
    }

    fun deleteSelectionNotes() {
        val selectionNotes = notes.value?.filter { it.selected } ?: return
        mainViewModel.deleteNotes(selectionNotes.map { it.note.id })
    }

    fun clearSelectedNotes() {
        val newNotes = notes.value?.toMutableList().apply {
            this?.replaceAll { it.copy(selected = false) }
        } ?: return
        _notes.postValue(newNotes.sortedBy { it.note.id })
    }

    fun makeNoteAnd(function: (Int?) -> Unit) = viewModelScope.launch {
        val ids = notes.value?.map { it.note.id } ?: emptyList()
        var noteId = 0
        while (true) {
            if (noteId !in ids) break
            noteId++
        }
        mainViewModel.makeEmptyNote(noteId)
        function(noteId)
    }
}