package com.example.movieapp.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
@Parcelize
data class Movie(val name: String, val content: String, val imgRes: Int) : Parcelable {
}