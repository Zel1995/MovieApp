package com.example.movieapp.di

import android.app.Application
import dagger.Module
import dagger.Provides

@Module
class ApplicationModule(private val app: App) {
    @Provides
    fun providersApplication(): Application = app
}