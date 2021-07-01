package com.example.movieapp.ui.list

import android.app.Application
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movieapp.domain.*
import com.example.movieapp.domain.serviceRequest.CatchMovieService
import java.util.concurrent.Executors

class MovieListViewModel(
    private val application: Application,
    private val repository: Repository
) : ViewModel() {
    private val executor = Executors.newSingleThreadExecutor()

    private val _movieLiveDada = MutableLiveData<List<MovieCategory>>()
    private val _loadingLiveDada = MutableLiveData(false)
    private val _errorLiveDada = MutableLiveData<String>()

    val movieLiveDada: LiveData<List<MovieCategory>> = _movieLiveDada
    val loadingLiveDada: LiveData<Boolean> = _loadingLiveDada
    val errorLiveDada: LiveData<String> = _errorLiveDada

    fun fetchMovies() {
        _loadingLiveDada.value = true
        repository.getMovies(executor) {
            when (it) {
                is Success -> {
                    val result: List<MovieCategory> = it.value
                    _movieLiveDada.value = result
                }
                is Error -> {
                    _errorLiveDada.value = it.value.stackTraceToString()
                }
            }
            _loadingLiveDada.value = false
        }
    }

    fun fetchServiceMovies() {
        _loadingLiveDada.value = true
        application.applicationContext.startService(
            Intent(
                application.applicationContext,
                CatchMovieService::class.java
            )
        )
        _loadingLiveDada.value = false
    }

    inner class MovieResultReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val result = mutableListOf<MovieCategory>()
            result.apply {
                intent?.getParcelableExtra<MovieCategory>(MovieRepositoryImpl.CATEGORY_POPULAR)
                    ?.let { add(it) }
                intent?.getParcelableExtra<MovieCategory>(MovieRepositoryImpl.CATEGORY_TOP_RATED)
                    ?.let { add(it) }
                intent?.getParcelableExtra<MovieCategory>(MovieRepositoryImpl.CATEGORY_NOW_PLAYING)
                    ?.let { add(it) }
                intent?.getParcelableExtra<MovieCategory>(MovieRepositoryImpl.CATEGORY_UPCOMING)
                    ?.let { add(it) }
            }
            _movieLiveDada.value = result

        }
    }

    override fun onCleared() {
        super.onCleared()
        executor.shutdown()
    }

}