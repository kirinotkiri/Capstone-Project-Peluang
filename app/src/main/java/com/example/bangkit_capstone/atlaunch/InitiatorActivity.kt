package com.example.bangkit_capstone.atlaunch

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.bangkit_capstone.R
import com.example.bangkit_capstone.di.Injection
import com.example.bangkit_capstone.home.HomeActivity
import com.example.bangkit_capstone.ui.auth.login.LoginActivity

class InitiatorActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()

        /*
        for some reason the splash screen doesn't set the theme back after launch, so this piece of code is
        necessary to set the theme back to the default theme
         */setTheme(R.style.Theme_Bangkit_Capstone)

        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        setContentView(R.layout.activity_initiator)

        val logon = Injection.loginProvider(this@InitiatorActivity)
        logon.isNew().observe(this@InitiatorActivity){
            if(it){
                val intent = Intent(this, LoginOrRegisterActivity::class.java)
                startActivity(intent)
                finish()
            }else{
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

    }
}