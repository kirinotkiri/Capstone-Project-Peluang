package com.example.bangkit_capstone.network

import com.example.bangkit_capstone.response.LoginResponse
import com.example.bangkit_capstone.response.RegisterResponse
import com.example.bangkit_capstone.response.RegistrationRequest
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

interface ApiService {

    @POST("api/register")
    suspend fun register(
        @Body registrationRequest: RegistrationRequest
    ): RegisterResponse

    @FormUrlEncoded
    @POST("login")
    suspend fun login(
        @Field("username") username: String,
        @Field("password") password: String
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