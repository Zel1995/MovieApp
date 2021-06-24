package com.example.movieapp.domain.repository

import com.example.movieapp.domain.model.MovieCategory
import com.example.movieapp.domain.repository.RepositoryResult
import java.util.concurrent.ExecutorService

interface Repository {
    fun getMovies(
        executor: ExecutorService,
        callback: (result: RepositoryResult<List<MovieCategory>>) -> Unit
    )

    fun getMovies(): RepositoryResult<List<MovieCategory>>
}