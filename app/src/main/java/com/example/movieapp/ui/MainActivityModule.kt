package com.example.movieapp.ui

import androidx.appcompat.app.AppCompatActivity
import dagger.Module
import dagger.Provides

@Module
class MainActivityModule(val activity: AppCompatActivity) {
    @Provides
    fun providesFragmentManager() = activity.supportFragmentManager
}