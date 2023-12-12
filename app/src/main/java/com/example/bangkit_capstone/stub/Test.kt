package com.example.bangkit_capstone.stub

import androidx.lifecycle.liveData
import com.example.bangkit_capstone.network.ApiService
import com.example.bangkit_capstone.network.ApiStatus
import com.example.bangkit_capstone.network.Config
import com.example.bangkit_capstone.response.ErrorResponse
import com.google.gson.Gson
import retrofit2.HttpException

class Test {

    fun apiProvider(token: String): ApiService {
        return Config().getApiService(token)
    }

    fun userLoginTest(email: String, password: String) = liveData {
        emit(ApiStatus.Loading)
        val apiService = apiProvider("null")
        try {
            val response = apiService.login(email, password)
            emit(ApiStatus.Success(response))
        } catch (e: HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
            val errorMessage = errorBody.message
            emit(ApiStatus.Error(errorMessage ?: ""))
        }
    }

}