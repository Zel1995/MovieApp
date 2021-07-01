package com.example.movieapp.domain.serviceRequest

import android.app.IntentService
import android.content.Intent
import com.example.movieapp.di.App
import com.example.movieapp.domain.repository.MovieRetrofitRepositoryImpl
import javax.inject.Inject

class CatchMovieService : IntentService("CatchMovieService") {

    companion object {
        const val SERVICE_ACTION = "SERVICE_ACTION"
    }

    @Inject
    lateinit var repository: MovieRetrofitRepositoryImpl
    override fun onCreate() {
        super.onCreate()
        (applicationContext as App).appComponent.inject(this)
    }


    override fun onHandleIntent(intent: Intent?) {

    }

}

