package com.example.movieapp.domain.repository

import com.example.movieapp.BuildConfig
import com.example.movieapp.domain.model.Movie
import com.example.movieapp.domain.model.MovieCategory
import com.example.movieapp.domain.network.TmdbApi
import com.example.movieapp.domain.network.model.TmdbResponse
import com.example.movieapp.storage.MovieDao
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieRetrofitRepositoryImpl(private val tmdbApi: TmdbApi, private val movieDao: MovieDao) :
    Repository {
    companion object {
        const val CATEGORY_NOW_PLAYING = "now_playing"
        const val CATEGORY_TOP_RATED = "top_rated"
        const val CATEGORY_POPULAR = "popular"
        const val CATEGORY_UPCOMING = "upcoming"
        const val LANGUAGE_RU = "ru"
    }


    @Deprecated("use getMovies with coroutines")
    private fun addCategoryAndPost(
        category: String,
        categoryList: MutableList<MovieCategory>,
        callback: (result: RepositoryResult<List<MovieCategory>>) -> Unit
    ) {
        tmdbApi.getMovies(category, BuildConfig.TMDB_KEY, "ru")
            .enqueue(object : Callback<TmdbResponse> {
                override fun onResponse(
                    call: Call<TmdbResponse>,
                    response: Response<TmdbResponse>
                ) {

                    if (response.isSuccessful) {

                        val movieList = mutableListOf<Movie>()
                        response.body()?.let {
                            it.results.forEach { result ->
                                movieList.add(
                                    Movie(
                                        result.id,
                                        result.adult,
                                        result.title,
                                        result.overview,
                                        result.posterPath,
                                        result.releaseDate,
                                        result.voteAverage
                                    )
                                )
                            }
                            categoryList.add(MovieCategory(category, movieList))
                            callback(Success(categoryList))
                        }
                    } else {
                        callback(Error(Exception(response.code().toString() + category)))
                    }
                }

                override fun onFailure(call: Call<TmdbResponse>, t: Throwable) {
                    callback(Error(t))
                }
            })
    }


    override suspend fun getMovies(adults: Boolean): RepositoryResult<List<MovieCategory>> {
        try {
            val categoryList = listOf(
                MovieCategory(
                    CATEGORY_NOW_PLAYING,
                    getMoviesListByCategory(CATEGORY_NOW_PLAYING, adults)
                ),

                MovieCategory(
                    CATEGORY_TOP_RATED,
                    getMoviesListByCategory(CATEGORY_TOP_RATED, adults)
                ),

                MovieCategory(
                    CATEGORY_POPULAR,
                    getMoviesListByCategory(CATEGORY_POPULAR, adults)
                ),

                MovieCategory(
                    CATEGORY_UPCOMING,
                    getMoviesListByCategory(CATEGORY_UPCOMING, adults)
                ),
            )
            return Success(categoryList)
        } catch (exc: Exception) {
            return Error(exc)
        }
    }

    private suspend fun getMoviesListByCategory(category: String, adults: Boolean): List<Movie> {
        val response =
            tmdbApi.getMoviesSuspend(category, BuildConfig.TMDB_KEY, LANGUAGE_RU)
        val movieList = mutableListOf<Movie>()
        if (adults) {
            response.results.forEach { result ->
                movieList.add(
                    Movie(
                        result.id,
                        result.adult,
                        result.title,
                        result.overview,
                        result.posterPath,
                        result.releaseDate,
                        result.voteAverage
                    )
                )
            }
        } else {
            response.results.forEach { result ->
                if (!result.adult) {
                    movieList.add(
                        Movie(
                            result.id,
                            result.adult,
                            result.title,
                            result.overview,
                            result.posterPath,
                            result.releaseDate,
                            result.voteAverage
                        )
                    )
                }
            }
        }
        return movieList
    }

}

sealed class RepositoryResult<T>
data class Success<T>(val value: T) : RepositoryResult<T>()
data class Error<T>(val value: Throwable) : RepositoryResult<T>()