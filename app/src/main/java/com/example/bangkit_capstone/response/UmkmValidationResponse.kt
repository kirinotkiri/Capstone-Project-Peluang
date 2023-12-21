package com.example.bangkit_capstone.response

import com.google.gson.annotations.SerializedName

data class UmkmValidationResponse (
    @field:SerializedName("success")
    val success: String? = null,

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("umkmId")
    val umkmId: String? = null
)
