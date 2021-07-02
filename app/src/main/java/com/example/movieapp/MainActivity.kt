package com.example.movieapp

import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.movieapp.R.string.hello
import com.example.movieapp.di.App
import com.example.movieapp.domain.AirplaneModeReceiver
import com.example.movieapp.domain.router.MainRouter
import com.example.movieapp.ui.MainActivityModule
import com.example.movieapp.ui.MainSubcomponent
import com.google.android.material.bottomnavigation.BottomNavigationView
import javax.inject.Inject

class MainActivity : AppCompatActivity(R.layout.main_activity),
    BottomNavigationView.OnNavigationItemSelectedListener {
    @Inject
    lateinit var router: MainRouter
    private lateinit var receiver: AirplaneModeReceiver
    var mainSubcomponent: MainSubcomponent? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainSubcomponent =
            (application as App).appComponent.mainComponent().create(MainActivityModule(this))
        mainSubcomponent?.inject(this)
        if (savedInstanceState == null) router.openMovieListFragment()

        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottomNavigationView)
        bottomNavigationView.setOnNavigationItemSelectedListener(this)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.home -> {
                router.openMovieListFragment()
            }
            R.id.favorite -> {
                Toast.makeText(applicationContext, resources.getString(hello), Toast.LENGTH_SHORT)
                    .show()
            }
            R.id.history -> {
                router.openHistoryFragment()
            }
        }
        return true
    }

    override fun onStart() {
        super.onStart()
        receiver = AirplaneModeReceiver()
        registerReceiver(receiver, IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED))

    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(receiver)
    }

    override fun onDestroy() {
        super.onDestroy()
        mainSubcomponent = null
    }
}