package com.example.bangkit_capstone.ui.auth.login

import androidx.lifecycle.ViewModel
import com.example.bangkit_capstone.repository.Repository
import com.example.bangkit_capstone.response.LoginResponse
import com.example.bangkit_capstone.stub.StubLogin
import com.example.bangkit_capstone.stub.Test

class LoginViewModel (private val repository: Repository): ViewModel(){
    fun login(id: String, pass: String): LoginResponse {
        val stub = StubLogin()
        return stub.mockLogin(id, pass)
    }

    fun userLoginTest(id: String, pass: String) = repository.userLogin(id,pass)
}
