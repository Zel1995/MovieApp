package com.example.movieapp.storage

import android.content.Context
import android.content.Context.MODE_PRIVATE
import javax.inject.Inject

class MovieStorage @Inject constructor(private val context: Context) {
    companion object {
        private const val ADULTS_KEY = "ADULTS_CATEGORY"
    }

    private val sharedPreferences = context.getSharedPreferences(ADULTS_KEY, MODE_PRIVATE)
    var adults: Boolean
        get() = sharedPreferences.getBoolean(ADULTS_KEY, true)
        set(value) {
            sharedPreferences
                .edit()
                .putBoolean(ADULTS_KEY, value)
                .apply()
        }
}