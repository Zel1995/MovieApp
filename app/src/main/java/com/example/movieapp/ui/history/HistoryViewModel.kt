package com.example.movieapp.ui.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.domain.repository.LocalRepository
import com.example.movieapp.storage.MovieEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class HistoryViewModel(private val localRepository: LocalRepository) : ViewModel() {

    private val _history = MutableStateFlow<List<MovieEntity>>(listOf())
    private val _error = MutableSharedFlow<String>()

    val history: Flow<List<MovieEntity>> = _history
    val error: Flow<String> = _error

    fun fetchHistory() {
        viewModelScope.launch {
            val result = localRepository.getHistory()
            _history.value = result
        }
    }

    fun deleteHistory() {
        viewModelScope.launch {
            localRepository.clearHistory()
        }
        fetchHistory()
    }

    fun addMovieEntity(movieEntity: MovieEntity) {
        viewModelScope.launch {
            localRepository.addHistory(movieEntity)
        }
    }
}