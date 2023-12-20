package com.example.bangkit_capstone.ui.auth.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bangkit_capstone.repository.Repository
import kotlinx.coroutines.launch

class LoginViewModel (private val repository: Repository): ViewModel(){
    fun userLogin(id: String, pass: String) = repository.userLogin(id,pass)

    fun setLogin(id : String, name: String, token: String, tokenRefresh: String) {
        viewModelScope.launch {
            repository.setLogin(id, name, token, tokenRefresh)
        }
    }
}
