package com.example.movieapp.ui.list

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movieapp.domain.Error
import com.example.movieapp.domain.Movie
import com.example.movieapp.domain.RepositoryImpl
import com.example.movieapp.domain.Success
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class MovieListViewModel(
    private val application: Application,
    private val repository: RepositoryImpl
) : ViewModel() {
    private val executor: Executor = Executors.newSingleThreadExecutor()

    private val _movieLiveDada = MutableLiveData<List<Movie>>()
    private val _loadingLiveDada = MutableLiveData(false)
    private val _errorLiveDada = MutableLiveData<String>()

    val movieLiveDada: LiveData<List<Movie>> = _movieLiveDada
    val loadingLiveDada: LiveData<Boolean> = _loadingLiveDada
    val errorLiveDada: LiveData<String> = _errorLiveDada

    fun fetchMovies() {
        _loadingLiveDada.value = true
        repository.getMovies(executor, {
            when (it) {
                is Success -> {
                    val result: List<Movie> = it.value
                    _movieLiveDada.value = result
                }
                is Error -> {
                    _errorLiveDada.value = it.value.stackTraceToString()
                }
            }
            _loadingLiveDada.value = false
        })
    }

    override fun onCleared() {

        super.onCleared()
    }
}