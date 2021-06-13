package com.example.movieapp.di

import android.app.Application

class App : Application() {
    val appComponent = DaggerAppComponent.builder()
        .applicationModule(ApplicationModule(this)).build()
}