package com.example.movieapp.ui.detils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.domain.repository.NoteLocalRepository
import com.example.movieapp.storage.NoteEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class MoviesViewModel(private val noteLocalRepository: NoteLocalRepository) : ViewModel() {

    private val _note = MutableStateFlow<NoteEntity?>(null)

    val note = _note

    fun fetchNote(id: Int) {
        viewModelScope.launch {
            val result = noteLocalRepository.getNote(id)
            note.value = result
        }
    }

    fun addNote(noteEntity: NoteEntity) {
        viewModelScope.launch { noteLocalRepository.addNote(noteEntity) }
    }
}