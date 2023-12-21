package com.example.bangkit_capstone.response

import com.google.gson.annotations.SerializedName

data class GetUmkmByIdResponse(

	@field:SerializedName("data")
	val data: Data? = null,

	@field:SerializedName("success")
	val success: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class UmkmData(

	@field:SerializedName("targetMarket")
	val targetMarket: String? = null,

	@field:SerializedName("urbanVillage")
	val urbanVillage: String? = null,

	@field:SerializedName("city")
	val city: String? = null,

	@field:SerializedName("district")
	val district: String? = null,

	@field:SerializedName("umkmName")
	val umkmName: String? = null,

	@field:SerializedName("industry")
	val industry: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("userId")
	val userId: String? = null
)


