package com.example.bangkit_capstone.stub

import com.example.bangkit_capstone.response.LoginResponse
import com.example.bangkit_capstone.response.LoginResult

class StubLogin {
    private val debugID: String = "miku"
    private val debugPass: String = "password"
    fun getStubResult():LoginResult{
        return LoginResult("debug", "0", "90909")
    }
    fun stubLoginSuccess():LoginResponse{
        return LoginResponse(getStubResult(), false, "Login successful")
    }

    fun stubLoginFail():LoginResponse{
        return LoginResponse(null, true, "Login failed")
    }

    fun mockLogin(id: String, pass: String): LoginResponse {
        return if(id == debugID && pass == debugPass){
            stubLoginSuccess()
        }else{
            stubLoginFail()
        }
    }
    
}