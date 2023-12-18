package com.example.bangkit_capstone.di

import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import com.example.bangkit_capstone.network.Config
import com.example.bangkit_capstone.repository.Repository




object Injection {
    fun provideApplicationInfoMetadata(context: Context): Bundle? {
        return context.packageManager
            .getApplicationInfo(context.packageName, PackageManager.GET_META_DATA)
            .metaData
    }

    fun provideRepository(context: Context, endpoint: String, token: String): Repository {
        val config = Config()

        val loginProvider = loginProvider(context)

        Log.d("PROVIDEREPO", "provideRepository: $token")

        val apiService = config.getApiService(token, endpoint)

        return Repository.getInstance(apiService, loginProvider)
    }


    fun loginProvider(context: Context): LoginHandler {
        return LoginHandler(context.logon)
    }
}