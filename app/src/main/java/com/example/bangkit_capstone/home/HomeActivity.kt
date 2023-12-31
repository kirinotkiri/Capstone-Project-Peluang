package com.example.bangkit_capstone.home

import android.content.Intent
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.bangkit_capstone.R
import com.example.bangkit_capstone.atlaunch.LoginOrRegisterActivity
import com.example.bangkit_capstone.databinding.ActivityHomeBinding
import com.example.bangkit_capstone.di.Injection


class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        /*
        for some reason the splash screen doesn't set the theme back after launch, so this piece of code is
        necessary to set the theme back to the default theme
         */setTheme(R.style.Theme_Bangkit_Capstone_OnMain)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        supportActionBar?.hide()

        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val logon = Injection.loginProvider(this@HomeActivity)
        logon.isNew().observe(this@HomeActivity){
            if(it){
                val intent = Intent(this, LoginOrRegisterActivity::class.java)
                startActivity(intent)
                finish()
            }
        }


        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_home)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }


}