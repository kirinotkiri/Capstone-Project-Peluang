package com.example.bangkit_capstone.home.ui.account

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.bangkit_capstone.repository.Repository
import com.example.bangkit_capstone.response.Data
import com.example.bangkit_capstone.response.GetUserByIdResponse
import com.example.bangkit_capstone.response.LoginResult
import kotlinx.coroutines.launch

class AccountViewModel(private val repository: Repository) : ViewModel() {

    fun getUserSession () :LiveData<LoginResult> = repository.getUserSession().asLiveData()

    val detailUserById : LiveData<Data> = repository.userDetail
    fun getUserById(id : String) {
        repository.getDetailUserId(id)
    }

    fun setLogout() {
        viewModelScope.launch {
            repository.setLogout()
        }
    }
}