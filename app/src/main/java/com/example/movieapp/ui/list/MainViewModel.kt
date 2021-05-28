package com.example.movieapp.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movieapp.domain.Error
import com.example.movieapp.domain.Movie
import com.example.movieapp.domain.RepositoryImpl
import com.example.movieapp.domain.Success
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class MainViewModel : ViewModel() {
    private val repository: RepositoryImpl = RepositoryImpl()
    private val executor: Executor = Executors.newSingleThreadExecutor()

    private val _movieLiveDada = MutableLiveData<List<Movie>>()
    private val _loadingLiveDada = MutableLiveData(false)
    private val _errorLiveDada = MutableLiveData<String>()

    val movieLiveDada: LiveData<List<Movie>> = _movieLiveDada
    val loadingLiveDada: LiveData<Boolean> = _loadingLiveDada
    val errorLiveDada: LiveData<String> = _errorLiveDada

    fun fetchMovies() {
        _loadingLiveDada.value = true
        repository.getMovies(executor, { it ->
            when (it) {
                is Success -> {
                    val result: List<Movie> = it.value
                    _movieLiveDada.value = result
                }
                is Error -> {
                    _errorLiveDada.value = it.value.stackTraceToString()
                }
            }
        })
    }

}