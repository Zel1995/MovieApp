package com.example.movieapp.domain.responses

import com.google.gson.annotations.SerializedName

data class MtdbResponse(
    @SerializedName("results")
    val results: List<Results>
) {
}