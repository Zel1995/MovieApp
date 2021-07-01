package com.example.movieapp.domain.router

import androidx.fragment.app.FragmentManager
import dagger.Module
import dagger.Provides

@Module
class RouterModule {
    @Provides
    fun providesRouter(fragmentManager: FragmentManager): MainRouter = MainRouter(fragmentManager)
}