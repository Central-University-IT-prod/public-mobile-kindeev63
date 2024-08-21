package com.knomster.mobile_kindeev63.presentation.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.knomster.mobile_kindeev63.domain.entities.DetailPlaceData
import com.knomster.mobile_kindeev63.domain.entities.database.Note
import kotlinx.coroutines.launch

class PlaceDetailsScreenViewModel(val mainViewModel: MainViewModel) : ViewModel() {
    private val _detailPlaceData = MutableLiveData<DetailPlaceData?>(null)
    val detailPlaceData: LiveData<DetailPlaceData?> = _detailPlaceData

    fun setDetailPlaceData(placeId: String) {
        mainViewModel.getPlaceData(placeId) {
            _detailPlaceData.postValue(it)
        }
    }

    fun makeNoteAnd(
        allNotes: List<Note>,
        function: (Int?) -> Unit
    ) = viewModelScope.launch {
        val ids = allNotes.map { it.id }
        var noteId = 0
        while (true) {
            if (noteId !in ids) break
            noteId++
        }
        mainViewModel.makeEmptyNote(
            placeId = detailPlaceData.value?.id ?: "",
            noteId = noteId
        )
        function(noteId)
    }
}