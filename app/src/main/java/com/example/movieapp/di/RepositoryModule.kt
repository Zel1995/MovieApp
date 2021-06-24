package com.example.movieapp.di

import com.example.movieapp.domain.repository.MovieRetrofitRepositoryImpl
import com.example.movieapp.domain.network.TmdbApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class RepositoryModule {
    @Singleton
    @Provides
    fun provideRepository(tmdbApi: TmdbApi): MovieRetrofitRepositoryImpl =
        MovieRetrofitRepositoryImpl(tmdbApi)

    @Provides
    fun providesTmdbApi(): TmdbApi {
        val retrofit = Retrofit
            .Builder()
            .baseUrl("https://api.themoviedb.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(TmdbApi::class.java)
    }
}