package com.example.bangkit_capstone.network

import com.example.bangkit_capstone.response.LoginResponse
import com.example.bangkit_capstone.response.RegisterResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    @POST("api/register")
    suspend fun register(
        @Body registrationRequest: RegistrationRequest
    ): RegisterResponse

    @POST("api/login")
    suspend fun login(
        @Body loginRequest: LoginRequest
    ): LoginResponse

    /*
    @GET("stories")
    suspend fun getStoryFromAPI(
        @Query("page") page: Int,
        @Query("size") size: Int,
        @Query("location") location: Int,
    ): GetStoryResponse

    @Multipart
    @POST("stories")
    suspend fun uploadStory(
        @Part file: MultipartBody.Part,
        @Part("description") description: RequestBody,
    ): AddStoryResponse

     */
}

data class LoginRequest(
    val email: String,
    val password: String
)

data class RegistrationRequest(
    val username: String,
    val email: String,
    val password: String
)
