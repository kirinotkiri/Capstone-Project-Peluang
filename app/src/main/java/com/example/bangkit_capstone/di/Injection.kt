package com.example.bangkit_capstone.di

import android.content.Context
import com.example.bangkit_capstone.network.Config
import com.example.bangkit_capstone.repository.Repository

object Injection {
    fun provideRepository(context: Context): Repository {
        val config = Config()
//        val pref = UserPreference.getInstance(context.dataStore)
        val user = "" //runBlocking { pref.getUser().first() }
        val apiService = config.getApiService(user)
        return Repository.getInstance(apiService)
    }
}