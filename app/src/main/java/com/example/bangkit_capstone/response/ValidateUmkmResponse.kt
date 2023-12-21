package com.example.bangkit_capstone.response

import com.google.gson.annotations.SerializedName

data class ValidateUmkmResponse(

	@field:SerializedName("success")
	val success: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("umkmId")
	val umkmId: String? = null
)
