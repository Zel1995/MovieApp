package com.example.movieapp.di

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides

@Module
class ApplicationModule(private val app: App) {
    @Provides
    fun providersApplication(): Application = app

    @Provides
    fun providersContext(): Context = app
}