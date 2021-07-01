package com.example.movieapp.domain.network

import com.example.movieapp.domain.network.model.TmdbResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TmdbApi {
    @GET("3/movie/{category}")
    fun getMovies(
        @Path("category") category: String,
        @Query("api_key") apiKey: String,
        @Query("language") language: String
    ): Call<TmdbResponse>

    @GET("3/movie/{category}")
    suspend fun getMoviesSuspend(
        @Path("category") category: String,
        @Query("api_key") apiKey: String,
        @Query("language") language: String
    ): TmdbResponse
}