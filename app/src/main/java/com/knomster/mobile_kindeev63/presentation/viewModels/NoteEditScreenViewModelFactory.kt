package com.knomster.mobile_kindeev63.presentation.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class NoteEditScreenViewModelFactory(
    private val mainViewModel: MainViewModel,
    private val noteId: Int
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        try {
            return NoteEditScreenViewModel(mainViewModel, noteId) as T
        } catch (_: Exception) {
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}