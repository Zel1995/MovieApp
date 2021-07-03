package com.example.movieapp.storage

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "NoteTable")
data class NoteEntity(
    @PrimaryKey(autoGenerate = true)
    val noteId: Long,
    val movieId: Int,
    val note: String
)