package com.example.movieapp.ui.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.movieapp.domain.repository.LocalRepository
import javax.inject.Inject

class HistoryViewModelFactory @Inject constructor(private val localRepository: LocalRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HistoryViewModel::class.java)) {
            return HistoryViewModel(localRepository) as T
        } else
            throw IllegalStateException()

    }
}