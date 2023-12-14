package com.example.bangkit_capstone.ui.auth.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.bangkit_capstone.databinding.ActivityLoginBinding
import com.example.bangkit_capstone.network.ApiStatus
import com.example.bangkit_capstone.ui.auth.SignupActivity

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = LoginViewModel()
        binding.btnLogin.setOnClickListener {
            val id = binding.edId.editText?.text.toString()
            val pass = binding.edPassword.editText?.text.toString()

            /////////////////////////////////TEST
            viewModel.userLoginTest(id, pass).observe(this@LoginActivity) { result ->
                if (result != null) {
                    when (result) {
                        is ApiStatus.Loading -> {
                            //NOT USED FOR ANYTHING YET
                        }

                        is ApiStatus.Success -> {
                            if (result.data.loginResult != null) {
                                AlertDialog.Builder(this@LoginActivity).apply {
                                    setTitle("Login Successful")
                                    setPositiveButton("foo") { _, _ -> }
                                    create()
                                    show()
                                }
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
}
