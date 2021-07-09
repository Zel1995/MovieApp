package com.example.movieapp.location

import android.location.Location
import kotlinx.coroutines.flow.Flow

interface LocationRepository {
    suspend fun currentLocation(): Location?
    fun getFlowLocation(): Flow<Location>
    suspend fun address(location: Location) : String?
}