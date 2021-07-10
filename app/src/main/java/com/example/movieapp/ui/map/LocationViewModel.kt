package com.example.movieapp.ui.map

import android.location.Location
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.location.LocationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

class LocationViewModel(private val locationRepository: LocationRepository) : ViewModel() {

    private val _lastLocation = MutableSharedFlow<Location>()
    private val _address = MutableSharedFlow<String>()
    val address: Flow<String> = _address
    val lastLocation: Flow<Location> = _lastLocation

    fun getLocation() {
        viewModelScope.launch {
            locationRepository.currentLocation()?.let {
                _lastLocation.emit(it)
            }
        }
    }

    fun fetchAddress(location: Location) {
        viewModelScope.launch {
            locationRepository.address(location)?.let {
                _lastLocation.emit(location)
                _address.emit(it)
            }

        }
    }
}