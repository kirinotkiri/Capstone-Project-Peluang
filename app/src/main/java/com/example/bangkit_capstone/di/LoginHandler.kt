package com.example.bangkit_capstone.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import kotlinx.coroutines.flow.map

val Context.logon: DataStore<androidx.datastore.preferences.core.Preferences> by preferencesDataStore(name = "userLogin")

class LoginHandler(private val logon: DataStore<androidx.datastore.preferences.core.Preferences>) {
    private val TOKEN = stringPreferencesKey("token")
    private val NAME = stringPreferencesKey("name")

    suspend fun setLogin(token: String, name: String) {
        logon.edit { it[TOKEN] = token }
        logon.edit { it[NAME] = name }
    }

    fun getName(): LiveData<String>{
        return logon.data.map {it[NAME].toString()}.asLiveData()
    }

    fun getToken(): LiveData<String> {
        return logon.data.map { it[TOKEN].toString() }.asLiveData()
    }

    suspend fun logout() {
        logon.edit { it[TOKEN] = "" }
    }

}




