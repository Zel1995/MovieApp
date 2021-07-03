package com.example.movieapp.storage

import androidx.room.*

@Dao
interface MovieDao {

    @Query("SELECT * FROM MovieTable WHERE id = :historyId")
    suspend fun getMovieById(historyId: String): MovieEntity

    @Query("SELECT * FROM MovieTable")
    suspend fun getMovies(): List<MovieEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addMovie(movieEntity: MovieEntity)

    @Query("DELETE FROM MovieTable")
    suspend fun clearHistory()


}