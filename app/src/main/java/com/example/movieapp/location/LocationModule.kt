package com.example.movieapp.location

import dagger.Binds
import dagger.Module

@Module
abstract class LocationModule {

    @Binds
    abstract fun bindsLocationRepository(locationRepositoryImpl: LocationRepositoryImpl):LocationRepository
}