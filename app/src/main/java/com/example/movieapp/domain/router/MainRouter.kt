package com.example.movieapp.domain.router

import androidx.fragment.app.FragmentManager
import com.example.movieapp.R
import com.example.movieapp.domain.Movie
import com.example.movieapp.ui.detils.MovieFragment
import com.example.movieapp.ui.list.MainFragment

class MainRouter(private val supportFragmentManager: FragmentManager) {
    fun openMainFragment() {
        supportFragmentManager.beginTransaction().replace(R.id.main_container, MainFragment())
            .commit()
    }

    fun openMovieFragmentByMovie(movie: Movie) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, MovieFragment.newInstance(movie))
            .addToBackStack(movie.content)
            .commit()
    }
}