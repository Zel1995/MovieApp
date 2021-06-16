package com.example.movieapp

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.movieapp.R.string.hello
import com.example.movieapp.domain.router.MainRouter
import com.example.movieapp.domain.router.RouterHolder
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity(R.layout.main_activity), RouterHolder,
    BottomNavigationView.OnNavigationItemSelectedListener {
    private val mainRouter: MainRouter = MainRouter(supportFragmentManager)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            mainRouter.openMainFragment()
        }
        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottomNavigationView)
        val toolbar:Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        bottomNavigationView.setOnNavigationItemSelectedListener(this)
    }

    override val router: MainRouter
        get() = mainRouter

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.home->{router.openMainFragment()}
            R.id.favorite->{
                Toast.makeText(applicationContext,resources.getString(hello),Toast.LENGTH_SHORT).show()}
            R.id.rating->{}
        }
        return true
    }
}