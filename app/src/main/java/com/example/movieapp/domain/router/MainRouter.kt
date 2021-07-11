package com.example.movieapp.domain.router

import androidx.fragment.app.FragmentManager
import com.example.movieapp.R
import com.example.movieapp.domain.model.Movie
import com.example.movieapp.ui.contacts.ContactsFragment
import com.example.movieapp.ui.detils.MovieFragment
import com.example.movieapp.ui.history.HistoryFragment
import com.example.movieapp.ui.list.MovieListFragment
import com.example.movieapp.ui.map.MapsFragment
import com.google.android.gms.maps.MapFragment

class MainRouter(private val supportFragmentManager: FragmentManager) {
    fun openMovieListFragment() {
        supportFragmentManager.beginTransaction().replace(R.id.main_container, MovieListFragment())
            .commit()
    }

    fun openMovieFragmentByMovie(movie: Movie) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, MovieFragment.newInstance(movie))
            .addToBackStack(movie.title)
            .commit()
    }

    fun openHistoryFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, HistoryFragment()).commit()
    }

    fun openContactsFragment() {
        supportFragmentManager.beginTransaction().addToBackStack("contacts").replace(R.id.main_container, ContactsFragment())
            .commit()
    }
    fun openMapFragment(){
        supportFragmentManager.beginTransaction().addToBackStack("map").replace(R.id.main_container, MapsFragment())
            .commit()

    }
}