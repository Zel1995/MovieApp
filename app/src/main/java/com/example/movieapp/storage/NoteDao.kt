package com.example.movieapp.storage

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface NoteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addNote(noteEntity: NoteEntity)

    @Query("SELECT * FROM NoteTable WHERE movieId = :id")
    suspend fun selectNoteById(id: Int): NoteEntity
}