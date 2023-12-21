package com.example.bangkit_capstone.network


import com.example.bangkit_capstone.response.DeleteUserResponse
import com.example.bangkit_capstone.response.EditProfileResponse
import com.example.bangkit_capstone.response.GetUserByIdResponse
import com.example.bangkit_capstone.response.LoginResponse
import com.example.bangkit_capstone.response.RegisterResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {

    @POST("api/register")
    suspend fun register(
        @Body registrationRequest: RegistrationRequest
    ): RegisterResponse

    @POST("api/login")
    suspend fun login(
        @Body loginRequest: LoginRequest
    ): LoginResponse

    @GET("api/user/{id}")
    fun getDetailUser(
        @Path("id") id : String
    ) : Call<GetUserByIdResponse>

    @PUT("api/user/update/{id}")
    suspend fun editProfileuser(
        @Path("id") id : String,
        @Body editProfileRequest: EditProfileRequest
    ) : EditProfileResponse
    @DELETE("api/user/delete/{id}")
    suspend fun deleteUser(
        @Path("id") id : String,
    ) : DeleteUserResponse
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

data class EditProfileRequest(
    val username: String,
    val email: String,
    val password: String
)
