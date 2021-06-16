package com.example.movieapp.domain

import java.io.Serializable

data class Movie(val name: String, val content: String, val imgRes: Int) : Serializable {
}