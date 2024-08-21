package com.knomster.mobile_kindeev63.presentation.viewModels

import android.os.Handler
import android.os.Looper
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.knomster.mobile_kindeev63.domain.entities.database.Note
import com.knomster.mobile_kindeev63.presentation.entities.NoteState

class NoteEditScreenViewModel(private val mainViewModel: MainViewModel, private val noteId: Int) :
    ViewModel() {
    private var noteStates = arrayListOf<NoteState>()
    private var stateIndex = -1
    private val _currentNoteState = MutableLiveData<NoteState>()
    val currentNoteState: LiveData<NoteState> = _currentNoteState

    private val _showDatePickerDialog = MutableLiveData(false)
    val showDatePickerDialog: LiveData<Boolean> = _showDatePickerDialog

    init {
        mainViewModel.getNoteById(id = noteId) { note ->
            if (note == null) {
                Handler(Looper.getMainLooper()).post { noteStates.add(NoteState.empty) }
            } else {
                Handler(Looper.getMainLooper()).post {
                    addNoteState(
                        NoteState(
                            title = TextFieldValue(note.title),
                            text = TextFieldValue(note.text),
                            date = note.date,
                            pinnedPlaceId = note.pinnedPlaceId
                        )
                    )
                }
            }
        }
    }

    private fun addNoteState(noteState: NoteState) {
        if (stateIndex != -1) {
            val statesToDelete =
                noteStates.filter { state -> noteStates.indexOf(state) > (noteStates.size + stateIndex) }
            noteStates.removeAll(statesToDelete.toSet())
            stateIndex = -1
        }
        noteStates.add(noteState)
        _currentNoteState.value = noteState
    }

    fun addNoteState(
        title: TextFieldValue? = null,
        text: TextFieldValue? = null,
        date: Long? = null
    ) {
        val oldState = getState()
        addNoteState(
            NoteState(
                title = title ?: oldState.title,
                text = text ?: oldState.text,
                date = date ?: oldState.date,
                pinnedPlaceId = oldState.pinnedPlaceId
            )
        )
    }

    fun redoState() {
        if (stateIndex < -1) stateIndex++
        _currentNoteState.value = getState()
    }

    fun undoState() {
        if (noteStates.size + stateIndex > 0) stateIndex--
        _currentNoteState.value = getState()
    }

    private fun getState() = noteStates[noteStates.size + stateIndex]

    fun saveNoteAnd(function: () -> Unit) {
        val state = getState()
        mainViewModel.insertNoteAnd(
            note = Note(
                id = noteId,
                date = state.date,
                title = state.title.text,
                text = state.text.text,
                pinnedPlaceId = state.pinnedPlaceId
            ),
            function = function
        )
    }

    fun showDatePickerDialog() {
        _showDatePickerDialog.postValue(true)
    }

    fun closeDatePickerDialog() {
        _showDatePickerDialog.postValue(false)
    }
}