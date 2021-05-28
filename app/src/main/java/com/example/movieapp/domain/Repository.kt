package com.example.movieapp.domain

import java.util.concurrent.Executor

interface Repository {
    fun getMovies(executor: Executor, callback: (result: RepositoryResult<List<Movie>>) -> Unit)
}