package com.example.bangkit_capstone.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.example.bangkit_capstone.di.LoginHandler
import com.example.bangkit_capstone.network.ApiService
import com.example.bangkit_capstone.network.ApiStatus
import com.example.bangkit_capstone.network.LoginRequest
import com.example.bangkit_capstone.network.RegistrationRequest
import com.example.bangkit_capstone.response.Data
import com.example.bangkit_capstone.response.ErrorResponse
import com.example.bangkit_capstone.response.GetUserByIdResponse
import com.example.bangkit_capstone.response.LoginResult
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response

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

    suspend fun setLogin(id : String,name: String, token: String, tokenRefresh: String) {
        logon.setLogin(id, name, token, tokenRefresh)
    }

    fun getToken(): LiveData<String> {
        return logon.getToken()
    }

    fun getUserSession() : Flow<LoginResult> {
        return logon.getUserSession()
    }

    suspend fun setLogout() {
        logon.logout()
    }

    private val _users = MutableLiveData<Data>()
    val userDetail : LiveData<Data> = _users

    fun getDetailUserId(id : String){
        apiService.getDetailUser(id)
            .enqueue(object : Callback<GetUserByIdResponse> {
                override fun onResponse(
                    call: Call<GetUserByIdResponse>,
                    response: Response<GetUserByIdResponse>
                ) {
                    if (response.isSuccessful){
                        _users.postValue(response.body()?.data!!)
                    }
                }

                override fun onFailure(call: Call<GetUserByIdResponse>, t: Throwable) {
                    ApiStatus.Error(t.message ?: "")
                }

            })
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