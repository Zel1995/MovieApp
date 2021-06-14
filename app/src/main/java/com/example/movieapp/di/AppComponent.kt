package com.example.movieapp.di

import com.example.movieapp.MainActivity
import com.example.movieapp.ui.list.MovieListFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class, RepositoryModule::class])
interface AppComponent {
    fun inject(main: MainActivity)
    fun inject(main: MovieListFragment)
}