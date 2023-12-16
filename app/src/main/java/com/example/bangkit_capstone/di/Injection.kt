package com.example.bangkit_capstone.di

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import com.example.bangkit_capstone.network.Config
import com.example.bangkit_capstone.repository.Repository




object Injection {
    private fun provideApplicationInfo(context: Context): ApplicationInfo{
        return context.packageManager
            .getApplicationInfo(context.packageName, PackageManager.GET_META_DATA)
    }

    fun provideAuthRepository(context: Context): Repository? {
        val endpoint = provideApplicationInfo(context).metaData.getString("ENDPOINT_AUTH")?: ""
        return if (endpoint.isNotEmpty()) {
            provideRepository(context, endpoint)
        } else {
            null
        }
    }

    fun provideUmkmRepository(context: Context): Repository? {
        val endpoint = provideApplicationInfo(context).metaData.getString("ENDPOINT_UMKM")?: ""
        return if (endpoint.isNotEmpty()) {
            provideRepository(context, endpoint)
        } else {
            null
        }
    }

    fun provideRepository(context: Context, endpoint: String): Repository {
        val config = Config()
//        val pref = UserPreference.getInstance(context.dataStore)
        val user = "" //runBlocking { pref.getUser().first() }
        val apiService = config.getApiService(user, endpoint)

        return Repository.getInstance(apiService, loginProvider(context))
    }


    fun loginProvider(context: Context): LoginHandler {
        return LoginHandler(context.logon)
    }
}