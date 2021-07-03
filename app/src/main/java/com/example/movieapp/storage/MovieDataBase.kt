package com.example.movieapp.storage

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [MovieEntity::class, NoteEntity::class], version = 2)
abstract class MovieDataBase : RoomDatabase() {
    abstract fun getMovieDao(): MovieDao

    abstract fun getNoteDao(): NoteDao
}