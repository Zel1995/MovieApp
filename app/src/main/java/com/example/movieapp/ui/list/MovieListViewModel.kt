package com.example.movieapp.ui.list

import android.app.Application
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.R
import com.example.movieapp.domain.model.MovieCategory
import com.example.movieapp.domain.repository.Error
import com.example.movieapp.domain.repository.MovieHttpsConnectionRepositoryImpl
import com.example.movieapp.domain.repository.Repository
import com.example.movieapp.domain.repository.Success
import com.example.movieapp.domain.serviceRequest.CatchMovieService
import com.example.movieapp.domain.usecases.ChangeAdultsCategoryUseCases
import com.example.movieapp.domain.usecases.FetchMoviesUseCase
import com.example.movieapp.storage.MovieStorage
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class MovieListViewModel(
    private val application: Application,
    private val fetchMoviesUseCase: FetchMoviesUseCase,
    private val changeAdultsCategoryUseCases: ChangeAdultsCategoryUseCases
) : ViewModel() {
    private val _movie = MutableStateFlow<List<MovieCategory>>(listOf())
    private val _loading = MutableStateFlow(false)
    private val _error = MutableSharedFlow<String>()
    private val _modeIcon: MutableStateFlow<Int>

    val movie: Flow<List<MovieCategory>> = _movie
    val loading: Flow<Boolean> = _loading
    val error: Flow<String> = _error

    init {
        val adultsMovie = changeAdultsCategoryUseCases.run()
        val icon = if (adultsMovie) R.drawable.ic_done_all else R.drawable.ic__filter_alt
        _modeIcon = MutableStateFlow(icon)
    }

    val modeIcon: Flow<Int> = _modeIcon

    fun fetchMovies() {
        _loading.value = true
        viewModelScope.launch {
            when (val result = fetchMoviesUseCase.run()) {
                is Success -> {
                    val movies = result.value
                    _movie.value = movies
                }
                is Error -> {
                    _error.emit(result.value.stackTraceToString())
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
    }

    fun switchAdultsCategory() {
        val adultsMovie = changeAdultsCategoryUseCases.run()
        val icon = if (adultsMovie) R.drawable.ic_done_all else R.drawable.ic__filter_alt
        _modeIcon.value = icon
    }

}