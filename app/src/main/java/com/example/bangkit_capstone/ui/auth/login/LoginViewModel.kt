package com.example.bangkit_capstone.ui.auth.login

import com.example.bangkit_capstone.response.LoginResponse
import com.example.bangkit_capstone.stub.StubLogin

class LoginViewModel {
    fun login(id: String, pass: String): LoginResponse {
        val stub = StubLogin()
        return stub.mockLogin(id, pass)
    }
}
