package com.example.bangkit_capstone.response

import com.example.bangkit_capstone.network.ApiStatus

data class GetUmkmByIdResponse (
    val success: String,
    val message: String,
    val data: umkmData
)

data class umkmData(
    val id: String,
    val userId: String,
    val umkmName: String,
    val industry: String,
    val targetMarket: String,
    val city: String,
    val district: String,
    val urbanVillage: String,
)

