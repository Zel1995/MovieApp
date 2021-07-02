package com.example.movieapp.domain.repository

import com.example.movieapp.domain.model.MovieCategory

interface Repository {
    suspend fun getMovies(adults: Boolean): RepositoryResult<List<MovieCategory>>
}