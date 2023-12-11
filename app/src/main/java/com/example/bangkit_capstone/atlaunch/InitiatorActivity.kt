package com.example.bangkit_capstone.atlaunch

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.bangkit_capstone.R
import com.example.bangkit_capstone.ui.auth.login.LoginActivity

class InitiatorActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_initiator)

        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }
}