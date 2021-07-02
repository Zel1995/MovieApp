package com.example.movieapp.domain.repository

import com.example.movieapp.BuildConfig
import com.example.movieapp.domain.model.Movie
import com.example.movieapp.domain.model.MovieCategory
import com.example.movieapp.domain.network.model.TmdbResponse
import com.google.gson.Gson
import java.net.URL
import javax.net.ssl.HttpsURLConnection

@Deprecated("repository for use https connection")
object MovieHttpsConnectionRepositoryImpl : Repository {

    private const val BASE_CATEGORY_URL =
        "https://api.themoviedb.org/3/movie/%s?api_key=${BuildConfig.TMDB_KEY}&language=%s"
    private const val REQUEST_LANGUAGE = "ru"
    const val CATEGORY_POPULAR = "popular"
    const val CATEGORY_TOP_RATED = "top_rated"
    const val CATEGORY_NOW_PLAYING = "now_playing"
    const val CATEGORY_UPCOMING = "upcoming"

    override suspend fun getMovies(adults: Boolean): RepositoryResult<List<MovieCategory>> {
        try {
            val result = listOf(
                loadMoviesCategory(CATEGORY_POPULAR),
                loadMoviesCategory(CATEGORY_TOP_RATED),
                loadMoviesCategory(CATEGORY_NOW_PLAYING),
                loadMoviesCategory(CATEGORY_UPCOMING),
            )
            return Success(result)
        } catch (e: Exception) {
            return Error(e)
        }
    }

    private fun loadMoviesCategory(category: String): MovieCategory {
        val url = URL(String.format(BASE_CATEGORY_URL, category, REQUEST_LANGUAGE))
        val connection = url.openConnection() as HttpsURLConnection
        val gson = Gson()
        with(connection) {
            requestMethod = "GET"
            readTimeout = 30_000
            val response =
                gson.fromJson(this.inputStream.bufferedReader(), TmdbResponse::class.java)

            val moviesList = mutableListOf<Movie>()
            response.results.forEach { results ->
                moviesList.add(
                    Movie(
                        results.id,
                        results.adult,
                        results.title,
                        results.overview,
                        results.posterPath,
                        results.releaseDate,
                        results.voteAverage
                    )
                )
            }

            return MovieCategory(category, moviesList)

        }

    }
}

