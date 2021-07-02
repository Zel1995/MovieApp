package com.example.movieapp.domain.usecases

import com.example.movieapp.storage.MovieStorage
import javax.inject.Inject

class ChangeAdultsCategoryUseCases @Inject constructor(private val storage: MovieStorage) {

    fun run(): Boolean{
        val valueToSet = storage.adults.not()
        storage.adults = valueToSet
        return valueToSet
    }

}