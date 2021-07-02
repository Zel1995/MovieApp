package com.example.movieapp.di

import com.example.movieapp.domain.serviceRequest.CatchMovieService
import com.example.movieapp.storage.DatabaseModule
import com.example.movieapp.ui.MainSubcomponent
import com.example.movieapp.ui.detils.MovieFragment
import com.example.movieapp.ui.history.HistoryFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class, RepositoryModule::class, DatabaseModule::class])
interface AppComponent {
    fun mainComponent(): MainSubcomponent.Factory
    fun inject(catchMovieService: CatchMovieService)
    fun inject(historyFragment: HistoryFragment)
    fun inject(movieFragment: MovieFragment)
}