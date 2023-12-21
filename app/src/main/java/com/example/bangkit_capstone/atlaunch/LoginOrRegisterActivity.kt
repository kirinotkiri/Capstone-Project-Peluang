package com.example.bangkit_capstone.atlaunch

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.bangkit_capstone.R
import com.example.bangkit_capstone.databinding.ActivityLoginOrRegisterBinding
import com.example.bangkit_capstone.di.Injection
import com.example.bangkit_capstone.home.HomeActivity
import com.example.bangkit_capstone.ui.auth.login.LoginActivity
import kotlinx.coroutines.runBlocking

class LoginOrRegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_or_register)

        val binding = ActivityLoginOrRegisterBinding.inflate(layoutInflater)

        val logon = Injection.loginProvider(this@LoginOrRegisterActivity)

        binding.apply{
            btnLoginOrSignup.setOnClickListener {
                val intent = Intent(this@LoginOrRegisterActivity, LoginActivity::class.java)
                startActivity(intent)
                runBlocking{
                    logon.setNotNew()
                    this@LoginOrRegisterActivity.finish()}

            }
            btnToHome.setOnClickListener {
                val intent = Intent(this@LoginOrRegisterActivity, HomeActivity::class.java)
                startActivity(intent)
                runBlocking{
                    logon.setNotNew()
                    this@LoginOrRegisterActivity.finish()}
            }
        }
    }
}