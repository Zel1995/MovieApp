package com.example.movieapp

import android.view.View
import androidx.annotation.StringRes
import com.google.android.material.snackbar.Snackbar

fun View.snack(@StringRes strId: Int) {
    setOnClickListener { Snackbar.make(this, strId, Snackbar.LENGTH_SHORT).show() }
}

fun View.snack(msg: String) {
    setOnClickListener { Snackbar.make(this, msg, Snackbar.LENGTH_SHORT).show() }
}