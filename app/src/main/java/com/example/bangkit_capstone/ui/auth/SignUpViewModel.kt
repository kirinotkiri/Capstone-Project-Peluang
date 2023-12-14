package com.example.bangkit_capstone.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.bangkit_capstone.repository.Repository
import kotlinx.coroutines.launch

class SignUpViewModel (private val repository: Repository) : ViewModel() {

    val isLoading : LiveData<Boolean> = repository.isLoading
    val message : LiveData<String?> = repository.singUpMessage

    fun signUp(username : String, email : String, password: String) {
        viewModelScope.launch {
             repository.signUp(username,email, password)
        }
    }

}