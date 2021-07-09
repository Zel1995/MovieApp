package com.example.movieapp.ui

import com.example.movieapp.MainActivity
import com.example.movieapp.domain.router.RouterModule
import com.example.movieapp.ui.detils.MovieFragment
import com.example.movieapp.ui.history.HistoryFragment
import com.example.movieapp.ui.list.MovieListFragment
import com.example.movieapp.ui.map.MapsFragment
import dagger.Subcomponent

@Subcomponent(modules = [RouterModule::class, MainActivityModule::class])
interface MainSubcomponent {
    @Subcomponent.Factory
    interface Factory {
        fun create(mainActivityModule: MainActivityModule): MainSubcomponent
    }

    fun inject(main: MainActivity)
    fun inject(main: MovieListFragment)
    fun inject(historyFragment: HistoryFragment)
    fun inject(movieFragment: MovieFragment)
    fun inject(mapsFragment: MapsFragment)
}