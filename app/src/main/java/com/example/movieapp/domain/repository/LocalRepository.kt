package com.example.movieapp.domain.repository

import com.example.movieapp.storage.MovieEntity

interface LocalRepository {
    suspend fun getHistory(): List<MovieEntity>

    suspend fun addHistory(movie: MovieEntity)

    suspend fun clearHistory()
}