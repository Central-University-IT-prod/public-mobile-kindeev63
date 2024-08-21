package com.knomster.mobile_kindeev63.presentation.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class AccountScreenViewModelFactory(private val mainViewModel: MainViewModel): ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        try {
            return AccountScreenViewModel(mainViewModel) as T
        } catch (_: Exception) {
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}