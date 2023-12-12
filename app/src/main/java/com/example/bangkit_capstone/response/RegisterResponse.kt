package com.example.bangkit_capstone.response

import com.google.gson.annotations.SerializedName

data class RegisterResponse(
    @field:SerializedName("succes")
    val error: Boolean? = null,

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("data")
    val registerResult: RegisterResult? = null
)

class RegisterResult (
    @field:SerializedName("id")
    val id: String? = null,

    @field:SerializedName("username")
    val name: String? = null,

    @field:SerializedName("email")
    val email: String? = null,

    @field:SerializedName("remember_token")
    val token: String? = null
)
