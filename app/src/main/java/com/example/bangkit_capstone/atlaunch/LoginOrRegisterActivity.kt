package com.example.bangkit_capstone.atlaunch

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.bangkit_capstone.R
import com.example.bangkit_capstone.databinding.ActivityLoginOrRegisterBinding
import com.example.bangkit_capstone.di.Injection
import com.example.bangkit_capstone.di.LoginHandler
import com.example.bangkit_capstone.home.HomeActivity
import com.example.bangkit_capstone.ui.auth.login.LoginActivity
import kotlinx.coroutines.runBlocking

class LoginOrRegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginOrRegisterBinding
    private lateinit var logon: LoginHandler
    override fun onCreate(savedInstanceState: Bundle?) {
        /*
        for some reason the splash screen doesn't set the theme back after launch, so this piece of code is
        necessary to set the theme back to the default theme
         */setTheme(R.style.Theme_Bangkit_Capstone)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_or_register)

        binding = ActivityLoginOrRegisterBinding.inflate(layoutInflater)

        logon = Injection.loginProvider(this@LoginOrRegisterActivity)
        Log.d("LOGINORREGISTER", "onCreate: TEST")

        /*
        binding.btnLoginOrSignup.setOnClickListener {
            Log.d("LOGINORREGISTER", "onCreate: to login activity")

            val intent = Intent(this@LoginOrRegisterActivity, LoginActivity::class.java)
            startActivity(intent)
            runBlocking {
                logon.setNotNew()
            }
            Log.d("LOGINORREGISTER", "onCreate: to login activity")
            finish()

        }

        binding.btnToHome.setOnClickListener {
            Log.d("LOGINORREGISTER", "onCreate: to home")

            val intent = Intent(this@LoginOrRegisterActivity, HomeActivity::class.java)
            startActivity(intent)
            runBlocking {
                logon.setNotNew()
            }
            Log.d("LOGINORREGISTER", "onCreate: to home")

            finish()
        }
         *///this is absolutely dumb but for some reason setOnClickListener refuses to work, WHY? Replaced it
        //with deprecated onClick attribute in the xml file
    }

    fun onClickedHere(view: View){
        when(view.id){
            R.id.btnLoginOrSignup ->{
                val intent = Intent(this@LoginOrRegisterActivity, LoginActivity::class.java)
                startActivity(intent)
                runBlocking {
                    logon.setNotNew()
                }
                finish()
            }

            R.id.btnToHome ->{
                val intent = Intent(this@LoginOrRegisterActivity, HomeActivity::class.java)
                startActivity(intent)
                runBlocking {
                    logon.setNotNew()
                }
                finish()
            }
        }
    }
}