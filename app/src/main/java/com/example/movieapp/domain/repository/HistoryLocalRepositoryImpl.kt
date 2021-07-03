package com.example.movieapp.domain.repository

import com.example.movieapp.storage.MovieDao
import com.example.movieapp.storage.MovieEntity
import javax.inject.Inject

class HistoryLocalRepositoryImpl @Inject constructor(private val movieDao: MovieDao) :
    LocalRepository {
    override suspend fun getHistory(): List<MovieEntity> {
        return movieDao.getMovies()
    }

    override suspend fun addHistory(movie: MovieEntity) {
        movieDao.addMovie(movie)
    }

    override suspend fun clearHistory() {
        movieDao.clearHistory()
    }
}