package com.example.movieapp.storage

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideMovieDataBase(application: Application): MovieDataBase =
        Room.databaseBuilder(application, MovieDataBase::class.java, "MovieDataBase")
            .fallbackToDestructiveMigration().build()

    @Provides
    fun providesMovieDao(dataBase: MovieDataBase): MovieDao = dataBase.getMovieDao()

    @Provides
    fun providesNotesDao(dataBase: MovieDataBase): NoteDao = dataBase.getNoteDao()
}