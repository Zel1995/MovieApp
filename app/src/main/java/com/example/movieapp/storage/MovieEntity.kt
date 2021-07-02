package com.example.movieapp.storage

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "MovieTable")
data class MovieEntity(
    @PrimaryKey(autoGenerate = true)
    val historyId: Long,
    val id: Int,
    val adult: Boolean,
    val title: String,
    val overview: String,
    val posterPath: String,
    val releaseDate: String?,
    val voteAverage: Float,
    val creationDate: String
) {
}