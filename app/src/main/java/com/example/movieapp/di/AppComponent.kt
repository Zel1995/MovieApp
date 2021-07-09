package com.example.movieapp.di

import com.example.movieapp.domain.serviceRequest.CatchMovieService
import com.example.movieapp.location.LocationModule
import com.example.movieapp.storage.DatabaseModule
import com.example.movieapp.ui.MainSubcomponent
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class, RepositoryModule::class, DatabaseModule::class, LocationModule::class])
interface AppComponent {
    fun mainComponent(): MainSubcomponent.Factory
    fun inject(catchMovieService: CatchMovieService)
}