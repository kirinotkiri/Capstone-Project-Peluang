package com.example.bangkit_capstone.ui.validateUmkm

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bangkit_capstone.repository.Repository
import com.example.bangkit_capstone.response.UmkmData
import kotlinx.coroutines.launch

class ValidateUmkmViewModel(private val repository: Repository) : ViewModel() {

    val message : LiveData<String?> = repository.validateMessage
    val umkmData : LiveData<String?> = repository.umkmDataById
    fun getUmkmById(userId: String) {
        viewModelScope.launch {
            repository.getUmkmById(userId)
        }
    }

    fun validateUmkm(
        userId : String,
        umkmName : String,
        industry : String,
        targetMarket : String,
        city : String,
        district : String,
        urbanVillage : String
    )
    {
        viewModelScope.launch {
            repository.validateUmkm(
                userId, umkmName, industry, targetMarket, city, district, urbanVillage
            )
        }
    }
}