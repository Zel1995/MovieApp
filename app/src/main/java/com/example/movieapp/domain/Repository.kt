package com.example.movieapp.domain

import java.util.concurrent.ExecutorService

interface Repository {
    fun getMovies(
        executor: ExecutorService,
        callback: (result: RepositoryResult<List<MovieCategory>>) -> Unit
    )

    fun getMovies(): RepositoryResult<List<MovieCategory>>
}