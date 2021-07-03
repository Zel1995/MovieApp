package com.example.movieapp.domain.repository

import com.example.movieapp.domain.model.MovieCategory

@Deprecated("this repository just for test without internet")
class MockRepositoryImpl : Repository {

    override suspend fun getMovies(adults: Boolean): RepositoryResult<List<MovieCategory>> {

        return Success(listOf())
    }
}

