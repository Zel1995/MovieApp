package com.example.movieapp.di

import com.example.movieapp.domain.MovieRepositoryImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {
    @Singleton
    @Provides
    fun provideRepository() = MovieRepositoryImpl
}