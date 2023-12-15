package com.example.bangkit_capstone.response

import com.google.gson.annotations.SerializedName

data class LoginResponse(

    @field:SerializedName("data")
    val loginResult: LoginResult? = null,

    @field:SerializedName("succes")
    val succes: Boolean? = null,

    @field:SerializedName("message")
    val message: String? = null
)

data class LoginResult(
    @field:SerializedName("id")
    val id: String? = null,

    @field:SerializedName("username")
    val name: String? = null,

    @field:SerializedName("accessToken")
    val accToken: String? = null,

    @field:SerializedName("refreshToken")
    val refToken: String? = null
)
