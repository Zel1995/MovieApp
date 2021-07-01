package com.example.movieapp.ui.list

import android.app.Application
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.domain.model.MovieCategory
import com.example.movieapp.domain.repository.Error
import com.example.movieapp.domain.repository.MovieHttpsConnectionRepositoryImpl
import com.example.movieapp.domain.repository.Repository
import com.example.movieapp.domain.repository.Success
import com.example.movieapp.domain.serviceRequest.CatchMovieService
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.concurrent.Executors

class MovieListViewModel(
    private val application: Application,
    private val repository: Repository
) : ViewModel() {
    private val executor = Executors.newSingleThreadExecutor()

    private val _movie = MutableStateFlow<List<MovieCategory>?>(null)
    private val _loading = MutableStateFlow(false)
    private val _error = MutableStateFlow<String?>(null)

    val movie: StateFlow<List<MovieCategory>?> = _movie
    val loading: StateFlow<Boolean> = _loading
    val error: StateFlow<String?> = _error

    @Deprecated("use with coroutines method fetchMovies")
    fun fetchMoviesWithCallback() {
        _loading.value = true
        repository.getMovies(executor) {
            when (it) {
                is Success -> {
                    val result: List<MovieCategory> = it.value
                    _movie.value = result
                }
                is Error -> {
                    _error.value = it.value.stackTraceToString()
                }
            }
            _loading.value = false
        }
    }

    fun fetchMovies() {
        _loading.value = true
        viewModelScope.launch {
            when (val result = repository.getMovies()) {
                is Success -> {
                    val movies = result.value
                    _movie.value = movies
                }
                is Error -> {
                    _error.value = result.value.stackTraceToString()
                }
            }
        }
        _loading.value = false
    }

    @Deprecated("just for test services")
    fun fetchServiceMovies() {
        _loading.value = true
        application.applicationContext.startService(
            Intent(
                application.applicationContext,
                CatchMovieService::class.java
            )
        )
        _loading.value = false
    }

    inner class MovieResultReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val result = mutableListOf<MovieCategory>()
            result.apply {
                intent?.getParcelableExtra<MovieCategory>(MovieHttpsConnectionRepositoryImpl.CATEGORY_POPULAR)
                    ?.let { add(it) }
                intent?.getParcelableExtra<MovieCategory>(MovieHttpsConnectionRepositoryImpl.CATEGORY_TOP_RATED)
                    ?.let { add(it) }
                intent?.getParcelableExtra<MovieCategory>(MovieHttpsConnectionRepositoryImpl.CATEGORY_NOW_PLAYING)
                    ?.let { add(it) }
                intent?.getParcelableExtra<MovieCategory>(MovieHttpsConnectionRepositoryImpl.CATEGORY_UPCOMING)
                    ?.let { add(it) }
            }
            _movie.value = result

        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
        executor.shutdown()
    }

}