package com.example.movieapp.location

import android.annotation.SuppressLint
import android.content.Context
import android.location.*
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import java.lang.RuntimeException
import javax.inject.Inject
import kotlin.concurrent.thread
import kotlin.coroutines.suspendCoroutine

class LocationRepositoryImpl @Inject constructor(private val context: Context) :
    LocationRepository {
    companion object {
        private const val REQUEST_TIMEOUT = 10000L
        private const val DISTANCE_ACCURACY = 10f
    }

    private val locationManager: LocationManager =
        context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

    @SuppressLint("MissingPermission")
    override suspend fun currentLocation(): Location? {
        val bestProvider = locationManager.getBestProvider(Criteria(), true)
        return locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
    }

    @SuppressLint("MissingPermission")
    override fun getFlowLocation(): Flow<Location> = callbackFlow<Location> {
        val bestProvider = locationManager.getBestProvider(Criteria(), true)
        val locationListener = LocationListener {
            trySend(it).isSuccess
        }
        locationManager.requestLocationUpdates(
            bestProvider ?: LocationManager.GPS_PROVIDER,
            REQUEST_TIMEOUT,
            DISTANCE_ACCURACY,
            locationListener
        )
        awaitClose {
            locationManager.removeUpdates(locationListener)
        }
    }

    override suspend fun address(location: Location): String? = suspendCoroutine {
        val geocoder = Geocoder(context)
        thread { val result = geocoder.getFromLocation(location.latitude, location.longitude, 1).firstOrNull()
            ?.getAddressLine(0)
            if(result == null){
                it.resumeWith(Result.failure(RuntimeException()))
            }else{
                it.resumeWith(Result.success(result))
            }
        }

    }
}