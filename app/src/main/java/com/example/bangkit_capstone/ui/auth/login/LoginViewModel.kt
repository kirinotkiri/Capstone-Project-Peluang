package com.example.bangkit_capstone.ui.auth.login

import androidx.lifecycle.ViewModel
import com.example.bangkit_capstone.repository.Repository

class LoginViewModel (private val repository: Repository): ViewModel(){
    fun userLogin(id: String, pass: String) = repository.userLogin(id,pass)
}
