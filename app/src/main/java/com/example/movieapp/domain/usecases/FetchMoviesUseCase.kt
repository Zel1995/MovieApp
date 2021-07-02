package com.example.movieapp.domain.usecases

import com.example.movieapp.domain.model.MovieCategory
import com.example.movieapp.domain.repository.MovieRetrofitRepositoryImpl
import com.example.movieapp.domain.repository.RepositoryResult
import com.example.movieapp.storage.MovieStorage
import javax.inject.Inject

class FetchMoviesUseCase @Inject constructor(
    private val repository: MovieRetrofitRepositoryImpl,
    private val storage: MovieStorage
) {
    suspend fun run():RepositoryResult<List<MovieCategory>>{
        val adults = storage.adults
        return repository.getMovies(adults)
    }
}