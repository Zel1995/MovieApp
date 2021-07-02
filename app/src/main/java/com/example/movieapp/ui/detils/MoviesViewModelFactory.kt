package com.example.movieapp.ui.detils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.movieapp.domain.repository.NoteLocalRepository

class MoviesViewModelFactory(private val noteLocalRepository:NoteLocalRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MoviesViewModel(noteLocalRepository) as T
    }
}