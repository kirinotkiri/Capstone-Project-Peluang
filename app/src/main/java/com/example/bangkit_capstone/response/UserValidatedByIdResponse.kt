package com.example.bangkit_capstone.response

import com.google.gson.annotations.SerializedName

data class UserValidatedByIdResponse(

	@field:SerializedName("userData")
	val userData: UserData? = null,

	@field:SerializedName("success")
	val success: Boolean? = null,

	@field:SerializedName("isUmkmRegistered")
	val isUmkmRegistered: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class UserData(

	@field:SerializedName("refresh_token")
	val refreshToken: String? = null,

	@field:SerializedName("password")
	val password: String? = null,

	@field:SerializedName("created_at")
	val createdAt: CreatedAtt? = null,

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("username")
	val username: String? = null
)

data class CreatedAtt(

	@field:SerializedName("_nanoseconds")
	val nanoseconds: Int? = null,

	@field:SerializedName("_seconds")
	val seconds: Int? = null
)
