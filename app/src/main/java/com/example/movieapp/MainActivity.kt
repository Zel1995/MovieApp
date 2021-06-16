package com.example.movieapp

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.movieapp.R.string.hello
import com.example.movieapp.di.App
import com.example.movieapp.domain.RepositoryImpl
import com.example.movieapp.domain.router.MainRouter
import com.example.movieapp.domain.router.RouterHolder
import com.google.android.material.bottomnavigation.BottomNavigationView
import javax.inject.Inject

class MainActivity : AppCompatActivity(R.layout.main_activity), RouterHolder,
    BottomNavigationView.OnNavigationItemSelectedListener {

    private val mainRouter: MainRouter = MainRouter(supportFragmentManager)
    override val router: MainRouter
        get() = mainRouter

    @Inject
    lateinit var repository: RepositoryImpl

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (application as? App)?.appComponent?.inject(this)
        if (savedInstanceState == null) mainRouter.openMovieListFragment()

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
            R.id.rating -> {
            }
        }
        return true
    }
}