package com.example.bangkit_capstone.ui.auth.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.bangkit_capstone.R
import com.example.bangkit_capstone.databinding.ActivityLoginBinding
import com.example.bangkit_capstone.di.Injection
import com.example.bangkit_capstone.home.HomeActivity
import com.example.bangkit_capstone.network.ApiStatus
import com.example.bangkit_capstone.response.LoginResult
import com.example.bangkit_capstone.ui.ViewModelFactory
import com.example.bangkit_capstone.ui.auth.SignupActivity

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        /*
        for some reason the splash screen doesn't set the theme back after launch, so this piece of code is
        necessary to set the theme back to the default theme
         */setTheme(R.style.Theme_Bangkit_Capstone)

        super.onCreate(savedInstanceState)

        val loginProviderToken = Injection.loginProvider(this).getToken().value ?: ""

        Injection.provideApplicationInfoMetadata(this)?.let { bundle ->
            bundle.getString("ENDPOINT_AUTH")?.let {
                viewModel = ViewModelProvider(
                    this,
                    ViewModelFactory.getInstance(this, it, loginProviderToken)
                )[LoginViewModel::class.java]
            }
        }

        if (::viewModel.isInitialized.not()) {
            Toast.makeText(this, "Unable to connect to login services", Toast.LENGTH_SHORT).show()
            finish()
        }

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {
            val id = binding.edId.editText?.text.toString()
            val pass = binding.edPassword.editText?.text.toString()

            /////////////////////////////////TEST
            viewModel.userLogin(id, pass).observe(this@LoginActivity) { result ->
                if (result != null) {
                    when (result) {
                        is ApiStatus.Loading -> {
                            //NOT USED FOR ANYTHING YET
                        }

                        is ApiStatus.Success -> {
                            if (result.data.loginResult != null) {
                                /*
                                AlertDialog.Builder(this@LoginActivity).apply {
                                    setTitle("Login Successful, ${result.data.loginResult.accToken}")
                                    setPositiveButton("foo") { _, _ ->
                                        val intent = Intent(this@LoginActivity, HomeActivity::class.java)
                                        startActivity(intent)
                                    }
                                    create()
                                    show()
                                }*/

                                setLogin(
                                    result.data.loginResult.id.toString(),
                                    result.data.loginResult.accToken.toString(),
                                    result.data.loginResult.name.toString(),
                                    result.data.loginResult.refToken.toString()
                                )
                                val intent = Intent(this@LoginActivity, HomeActivity::class.java)
                                startActivity(intent)

                                Log.d(
                                    "LOGINACTIVITY:ONLOGIN",
                                    "onCreate: ${result.data.loginResult}"
                                )
                            } else {
                                Toast.makeText(
                                    this@LoginActivity,
                                    "Login is Successful, however something went wrong",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }

                        is ApiStatus.Error -> {
                            Toast.makeText(
                                this@LoginActivity,
                                "Login Failed",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                    }
                }
            }
            ////////////////////////////////////////////////////////////////////////////////////


        }

        binding.tvRegist.setOnClickListener {
            val moveToSignUp = Intent(this, SignupActivity::class.java)
            startActivity(moveToSignUp)
        }

    }

    private fun setLogin(id : String, name : String, token : String, tokenRefresh : String) {
        viewModel.setLogin(
            id, name, token, tokenRefresh
        )
    }
}
