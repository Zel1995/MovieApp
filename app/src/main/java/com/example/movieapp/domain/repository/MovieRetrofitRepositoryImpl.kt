package com.example.movieapp.domain.repository

import com.example.movieapp.BuildConfig
import com.example.movieapp.domain.model.Movie
import com.example.movieapp.domain.model.MovieCategory
import com.example.movieapp.domain.network.TmdbApi
import com.example.movieapp.domain.network.model.TmdbResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.ExecutorService

class MovieRetrofitRepositoryImpl(private val tmdbApi: TmdbApi) : Repository {
    companion object {
        const val CATEGORY_NOW_PLAYING = "now_playing"
        const val CATEGORY_TOP_RATED = "top_rated"
        const val CATEGORY_POPULAR = "popular"
        const val CATEGORY_UPCOMING = "upcoming"
    }
    @Deprecated("use getMovies with coroutines")
    override fun getMovies(
        executor: ExecutorService,
        callback: (result: RepositoryResult<List<MovieCategory>>) -> Unit
    ) {
        val categoryList = mutableListOf<MovieCategory>()

        addCategoryAndPost(CATEGORY_NOW_PLAYING, categoryList, callback)
        addCategoryAndPost(CATEGORY_TOP_RATED, categoryList, callback)
        addCategoryAndPost(CATEGORY_POPULAR, categoryList, callback)
        addCategoryAndPost(CATEGORY_UPCOMING, categoryList, callback)
    }

    @Deprecated("use getMovies with coroutines and getMoviesListByCategory()")
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


    override suspend fun getMovies(): RepositoryResult<List<MovieCategory>> {
        try {
            val categoryList = listOf(
                MovieCategory(
                    CATEGORY_NOW_PLAYING,
                    getMoviesListByCategory(CATEGORY_NOW_PLAYING)
                ),

                MovieCategory(
                    CATEGORY_TOP_RATED,
                    getMoviesListByCategory(CATEGORY_TOP_RATED)
                ),

                MovieCategory(
                    CATEGORY_POPULAR,
                    getMoviesListByCategory(CATEGORY_POPULAR)
                ),

                MovieCategory(
                    CATEGORY_UPCOMING,
                    getMoviesListByCategory(CATEGORY_UPCOMING)
                ),
            )
            return Success(categoryList)
        } catch (exc: Exception) {
            return Error(exc)
        }
    }

    private suspend fun getMoviesListByCategory(category: String): List<Movie> {
        val response =
            tmdbApi.getMoviesSuspend(category, BuildConfig.TMDB_KEY, "ru")
        val movieList = mutableListOf<Movie>()
        response.results.forEach { result ->
            movieList.add(
                Movie(
                    result.id,
                    result.title,
                    result.overview,
                    result.posterPath,
                    result.releaseDate,
                    result.voteAverage
                )
            )
        }
        return movieList
    }

}

sealed class RepositoryResult<T>
data class Success<T>(val value: T) : RepositoryResult<T>()
data class Error<T>(val value: Throwable) : RepositoryResult<T>()