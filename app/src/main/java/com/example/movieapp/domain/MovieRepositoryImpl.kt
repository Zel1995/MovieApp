package com.example.movieapp.domain

import android.os.Handler
import android.os.Looper
import com.example.movieapp.BuildConfig
import com.example.movieapp.domain.responses.MtdbResponse
import com.google.gson.Gson
import java.net.URL
import java.util.concurrent.ExecutorService
import javax.net.ssl.HttpsURLConnection

object MovieRepositoryImpl : Repository {
    private const val BASE_CATEGORY_URL =
        "https://api.themoviedb.org/3/movie/%s?api_key=${BuildConfig.TMDB_KEY}&%s"
    private val handler: Handler = Handler(Looper.getMainLooper())
    override fun getMovies(
        executor: ExecutorService,
        callback: (result: RepositoryResult<List<MovieCategory>>) -> Unit
    ) {
        executor.execute {
            try {
                val result = listOf(
                    loadMoviesCategory("popular"),
                    loadMoviesCategory("top_rated"),
                    loadMoviesCategory("now_playing"),
                    loadMoviesCategory("upcoming"),
                )
                handler.post {
                    callback(Success(result))
                }
            } catch (e: Exception) {
                handler.post {
                    callback(Error(e))
                }
            }
        }
    }

    private fun loadMoviesCategory(category: String): MovieCategory {
        val url = URL(String.format(BASE_CATEGORY_URL, category, "language=ru"))
        val connection = url.openConnection() as HttpsURLConnection
        val gson = Gson()
        with(connection) {
            requestMethod = "GET"
            readTimeout = 30_000
            val response =
                gson.fromJson(this.inputStream.bufferedReader(), MtdbResponse::class.java)

            val moviesList = mutableListOf<Movie>()
            response.results.forEach { results ->
                moviesList.add(
                    Movie(
                        results.id,
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

sealed class RepositoryResult<T>
data class Success<T>(val value: T) : RepositoryResult<T>()
data class Error<T>(val value: Throwable) : RepositoryResult<T>()