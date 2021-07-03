package com.example.movieapp.domain.repository

import com.example.movieapp.storage.NoteEntity

interface NoteLocalRepository {
    suspend fun getNote(id: Int): NoteEntity

    suspend fun addNote(noteEntity: NoteEntity)
}