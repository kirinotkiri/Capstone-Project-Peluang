package com.example.bangkit_capstone.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.example.bangkit_capstone.response.LoginResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.logon: DataStore<androidx.datastore.preferences.core.Preferences> by preferencesDataStore(name = "userLogin")

class LoginHandler(private val logon: DataStore<androidx.datastore.preferences.core.Preferences>) {
    private val TOKEN = stringPreferencesKey("token")
    private val TOKEN_REFRESH = stringPreferencesKey("token_refresh")
    private val NAME = stringPreferencesKey("name")
    private val ID = stringPreferencesKey("id")
    private val IS_NOT_NEW = stringPreferencesKey("is_new")


    suspend fun setLogin(id: String, name: String, token: String, tokenRefresh: String) {
        logon.edit { it[ID] = id }
        logon.edit { it[NAME] = name }
        logon.edit { it[TOKEN] = token }
        logon.edit { it[TOKEN_REFRESH] = tokenRefresh }
    }


    fun getUserSession(): Flow<LoginResult> {
        return logon.data.map { pref ->
            val id: String = pref[ID] ?: ""
            val token: String = pref[TOKEN] ?: ""
            val name: String = pref[NAME] ?: ""
            val tokenRefresh: String = pref[TOKEN_REFRESH] ?: ""
            LoginResult(id, token, name, tokenRefresh)
        }
    }

    suspend fun setNotNew() {
        logon.edit { it[IS_NOT_NEW] = "NOT_NEW" }
    }

    fun isNew(): LiveData<Boolean> {
        return logon.data.map { it[IS_NOT_NEW] != "NOT_NEW" }.asLiveData()
    }

    fun getName(): LiveData<String>{
        return logon.data.map {it[NAME].toString()}.asLiveData()
    }

    fun getToken(): LiveData<String> {
        return logon.data.map { it[TOKEN].toString() }.asLiveData()
    }

    fun getTokenRefresh(): LiveData<String> {
        return logon.data.map { it[TOKEN_REFRESH].toString() }.asLiveData()
    }

    suspend fun logout() {
        //logon.edit { it[TOKEN] = "" }
        logon.edit { pref ->
            pref.clear()
        }
    }

}




