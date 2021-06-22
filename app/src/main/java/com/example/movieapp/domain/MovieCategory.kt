package com.example.movieapp.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieCategory(val name: String, val movies: List<Movie>):Parcelable