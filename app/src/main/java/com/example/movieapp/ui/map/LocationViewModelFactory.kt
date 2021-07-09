package com.example.movieapp.ui.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.movieapp.location.LocationRepository
import javax.inject.Inject

class LocationViewModelFactory @Inject constructor(private val locationRepository: LocationRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LocationViewModel::class.java)) {
            return LocationViewModel(locationRepository) as T
        } else {
            throw IllegalStateException()
        }
    }
}