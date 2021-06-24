package com.example.movieapp.domain.network.model

import com.google.gson.annotations.SerializedName

data class TmdbResponse(
    @SerializedName("results")
    val results: List<Results>
) {
}