package com.example.movieapp.di

import com.example.movieapp.domain.network.TmdbApi
import com.example.movieapp.domain.repository.*
import com.example.movieapp.storage.MovieDao
import com.example.movieapp.storage.NoteDao
import com.example.movieapp.ui.history.HistoryViewModelFactory
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class RepositoryModule {
    @Singleton
    @Provides
    fun provideRepository(tmdbApi: TmdbApi, movieDao: MovieDao): MovieRetrofitRepositoryImpl =
        MovieRetrofitRepositoryImpl(tmdbApi, movieDao)

    @Provides
    fun providesTmdbApi(): TmdbApi {
        val retrofit = Retrofit
            .Builder()
            .baseUrl("https://api.themoviedb.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(TmdbApi::class.java)
    }

    @Singleton
    @Provides
    fun providesLocalRepository(movieDao: MovieDao): LocalRepository =
        HistoryLocalRepositoryImpl(movieDao)

    @Provides
    fun providesNotesLocalRepository(noteDao: NoteDao): NoteLocalRepository =
        NoteLocalRepositoryImpl(noteDao)

    @Provides
    fun providesHistoryViewModelFactory(localRepository: LocalRepository): HistoryViewModelFactory =
        HistoryViewModelFactory(localRepository)

}