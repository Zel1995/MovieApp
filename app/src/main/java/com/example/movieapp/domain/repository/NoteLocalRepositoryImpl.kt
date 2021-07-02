package com.example.movieapp.domain.repository

import com.example.movieapp.storage.NoteDao
import com.example.movieapp.storage.NoteEntity
import javax.inject.Inject

class NoteLocalRepositoryImpl @Inject constructor(private val noteDao: NoteDao) :
    NoteLocalRepository {
    override suspend fun getNote(id: Int): NoteEntity {
        return noteDao.selectNoteById(id)
    }

    override suspend fun addNote(noteEntity: NoteEntity) {
        noteDao.addNote(noteEntity)
    }
}