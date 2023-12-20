package com.example.bangkit_capstone.response

import com.google.gson.annotations.SerializedName

data class DeleteUserResponse(

	@field:SerializedName("data")
	val data: UserDelateData? = null,

	@field:SerializedName("success")
	val success: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class CreatedAtDate(

	@field:SerializedName("_nanoseconds")
	val nanoseconds: Int? = null,

	@field:SerializedName("_seconds")
	val seconds: Int? = null
)

data class UserDelateData(

	@field:SerializedName("password")
	val password: String? = null,

	@field:SerializedName("created_at")
	val createdAt: CreatedAtDate? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("username")
	val username: String? = null
)
