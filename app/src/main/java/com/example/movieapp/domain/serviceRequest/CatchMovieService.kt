package com.example.movieapp.domain.serviceRequest

import android.app.IntentService
import android.content.Intent
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.movieapp.di.App
import com.example.movieapp.domain.MovieRepositoryImpl
import com.example.movieapp.domain.Success
import javax.inject.Inject

class CatchMovieService : IntentService("CatchMovieService") {

    companion object {
        public const val SERVICE_ACTION = "SERVICE_ACTION"
    }

    @Inject
    lateinit var repository: MovieRepositoryImpl
    override fun onCreate() {
        super.onCreate()
        (applicationContext as App).appComponent.inject(this)
    }


    override fun onHandleIntent(intent: Intent?) {
        val result = repository.getMovies()
        if (result is Success) {
            val intentForBroadcast = Intent(SERVICE_ACTION)
            result.value.forEach {
                intentForBroadcast.putExtra(it.name, it)
            }
            LocalBroadcastManager.getInstance(this).sendBroadcast(intentForBroadcast)
        }
    }

}
