package com.example.movieapp.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie(
    val id: Int,
    val adult:Boolean,
    val title: String,
    val overview: String,
    val posterPath: String,
    val releaseDate: String?,
    val voteAverage: Float
) : Parcelable