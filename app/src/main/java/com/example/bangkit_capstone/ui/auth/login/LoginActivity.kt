package com.example.bangkit_capstone.ui.auth.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.bangkit_capstone.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding : ActivityLoginBinding
    private lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = LoginViewModel()
        binding.btnLogin.setOnClickListener {
            val id = binding.edId.editText?.text.toString()
            val pass = binding.edPassword.editText?.text.toString()
            val response = viewModel.login(id, pass)
            if(response.error == false){
                //login success
                
            }else{
                //login failed

            }
        }


    }
}