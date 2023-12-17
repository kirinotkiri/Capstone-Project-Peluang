package com.example.bangkit_capstone.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.example.bangkit_capstone.di.LoginHandler
import com.example.bangkit_capstone.network.ApiService
import com.example.bangkit_capstone.network.ApiStatus
import com.example.bangkit_capstone.network.LoginRequest
import com.example.bangkit_capstone.network.RegistrationRequest
import com.example.bangkit_capstone.response.ErrorResponse
import com.google.gson.Gson
import kotlinx.coroutines.flow.internal.NopCollector.emit
import retrofit2.HttpException

class Repository
private constructor(
    private val apiService: ApiService,
    private val logon: LoginHandler
){

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> = _isLoading

    private val _singUpMessage = MutableLiveData<String?>()
    val singUpMessage: LiveData<String?> get() = _singUpMessage

    suspend fun signUp(username : String, email : String, password: String) {
        _isLoading.value = true
        try {
            //get success message
            val successMessage = apiService.register(RegistrationRequest(username, email, password)).message
            _isLoading.value = false
            _singUpMessage.value = successMessage
        } catch (e: HttpException) {
            //get error message
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
            val errorMessage = errorBody.message
            _isLoading.value = false
            _singUpMessage.value = errorMessage
        }
    }

    fun userLogin(email: String, password: String) = liveData {
        emit(ApiStatus.Loading)
        try {
            val response = apiService.login(LoginRequest(email, password))
            emit(ApiStatus.Success(response))
        } catch (e: HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
            val errorMessage = errorBody.message
            emit(ApiStatus.Error(errorMessage ?: ""))
        }
    }

    suspend fun setLogin(token: String, tokenRefresh: String ,name: String) {
        logon.setLogin(token, name, tokenRefresh)
    }

    suspend fun getToken(): LiveData<String> {
        return logon.getToken()
    }

    companion object {

        @Volatile
        private var instance: Repository? = null
        fun getInstance(
            apiService : ApiService,
            logon: LoginHandler
        ): Repository =
            instance ?: synchronized(this) {
                instance ?: Repository(apiService, logon)
            }.also { instance = it }

    }
}