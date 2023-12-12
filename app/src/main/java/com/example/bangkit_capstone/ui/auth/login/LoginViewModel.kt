package com.example.bangkit_capstone.ui.auth.login

import com.example.bangkit_capstone.response.LoginResponse
import com.example.bangkit_capstone.stub.StubLogin
import com.example.bangkit_capstone.stub.Test

class LoginViewModel {
    fun login(id: String, pass: String): LoginResponse {
        val stub = StubLogin()
        return stub.mockLogin(id, pass)
    }

    fun userLoginTest(id: String, pass: String) = Test().userLoginTest(id, pass)
}
