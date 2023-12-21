package com.example.bangkit_capstone.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.example.bangkit_capstone.di.LoginHandler
import com.example.bangkit_capstone.network.ApiService
import com.example.bangkit_capstone.network.ApiStatus
import com.example.bangkit_capstone.network.EditProfileRequest
import com.example.bangkit_capstone.network.LoginRequest
import com.example.bangkit_capstone.network.RegistrationRequest
import com.example.bangkit_capstone.network.UmkmData
import com.example.bangkit_capstone.network.ValidateUmkmRequest
import com.example.bangkit_capstone.response.Data
import com.example.bangkit_capstone.response.EditProfileResponse
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

    private val _editUserMessage = MutableLiveData<String?>()
    val editUserMessage: LiveData<String?> get() = _editUserMessage
    suspend fun editProfileUser(
        id : String,
        username: String,
        email: String,
        password: String
    ) {
        try {
            val response = apiService.editProfileuser(id, EditProfileRequest(username, email, password))
            ApiStatus.Success(response)
            _editUserMessage.postValue(response.message)
        } catch (e: HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
            val errorMessage = errorBody.message
            _editUserMessage.value = errorMessage
            ApiStatus.Error(errorMessage ?: "")
        }
    }

    private val _deleteUserMessage = MutableLiveData<String?>()
    val deleteUserMessage: LiveData<String?> get() = _deleteUserMessage
    suspend fun deleteUser(userId : String) {
        try {
            val response = apiService.deleteUser(userId).message
            _deleteUserMessage.postValue(response)
            ApiStatus.Success(response)
        } catch (e: HttpException){
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
            val errorMessage = errorBody.message
            ApiStatus.Error(errorMessage ?: "")
        }
    }

    private val _validateMessage = MutableLiveData<String?>()
    val validateMessage: LiveData<String?> get() = _validateMessage

    suspend fun validateUmkm(
        userId : String,
        umkmName : String,
        industry : String,
        targetMarket : String,
        city : String,
        district : String,
        urbanVillage : String
        )
    {
        val umkmValidate = ValidateUmkmRequest(
            userId,
            UmkmData(
                umkmName,
                industry,
                targetMarket,
                city,
                district,
                urbanVillage
            )
        )
        try {
            val response = apiService.validateUmkm("https://umkm-fmaxsvveia-et.a.run.app/api/validation/umkm",umkmValidate).message
            _validateMessage.postValue(response)
            ApiStatus.Success(response)
        } catch (e: HttpException){
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
            val errorMessage = errorBody.message
            _validateMessage.postValue(errorMessage)
            ApiStatus.Error("")
        }
    }

    private val _getUmkmById = MutableLiveData<String?>()
    val umkmDataById: LiveData<String?> get() = _getUmkmById
    suspend fun getUmkmById(userId : String) {
        try {
            val response = apiService.getUmkmByIdData("https://umkm-fmaxsvveia-et.a.run.app/api/validation/user/$userId")
            ApiStatus.Success(response)
            _getUmkmById.postValue(response.message)
            Log.d("respon get umkm by id", response.toString())
        } catch (e: HttpException){
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
            val errorMessage = errorBody.message
            _validateMessage.postValue(e.message)
            ApiStatus.Error("")
        }
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