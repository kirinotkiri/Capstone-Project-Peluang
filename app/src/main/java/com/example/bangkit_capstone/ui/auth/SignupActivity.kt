package com.example.bangkit_capstone.ui.auth

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.bangkit_capstone.R
import com.example.bangkit_capstone.databinding.ActivitySignupBinding
import com.example.bangkit_capstone.di.Injection
import com.example.bangkit_capstone.ui.ViewModelFactory
import com.example.bangkit_capstone.ui.auth.login.LoginActivity

class SignupActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignupBinding

    private lateinit var viewModel: SignUpViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        /*
        for some reason the splash screen doesn't set the theme back after launch, so this piece of code is
        necessary to set the theme back to the default theme
         */setTheme(R.style.Theme_Bangkit_Capstone)

        super.onCreate(savedInstanceState)

        val loginProviderToken = Injection.loginProvider(this).getToken().value?:""

        Injection.provideApplicationInfoMetadata(this)?.let { bundle ->
            bundle.getString("ENDPOINT_AUTH")?.let {
                viewModel = ViewModelProvider(
                    this,
                    ViewModelFactory.getInstance(this, it, loginProviderToken)
                )[SignUpViewModel::class.java]
            }
        }

        if (::viewModel.isInitialized.not()) {
            Toast.makeText(this, "Unable to connect to login services", Toast.LENGTH_SHORT).show()
            finish()
        }

        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSignup.setOnClickListener { signUp() }
        binding.tvRegist.setOnClickListener { startActivity(Intent(this, LoginActivity::class.java)) }
    }

    private fun signUp() {
        val username = binding.edUsername.text.toString()
        val email = binding.edEmail.text.toString()
        val password = binding.edPassword.text.toString()
        val confirmPassword = binding.edConfirmPassword.text.toString()

        if (password == confirmPassword) {
            viewModel.signUp(username, email, password)
            viewModel.message.observe(this) {
                if (it != null) {
                    message(it)
                }
                startActivity(Intent(this, LoginActivity::class.java))
            }
            viewModel.isLoading.observe(this) {
                showLoading(it)
            }
        } else {
            message("Password tidak cocok")
        }
    }

    private fun message(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}