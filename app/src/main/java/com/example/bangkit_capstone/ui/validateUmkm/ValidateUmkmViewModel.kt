package com.example.bangkit_capstone.ui.validateUmkm

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bangkit_capstone.repository.Repository
import kotlinx.coroutines.launch

class ValidateUmkmViewModel(private val repository: Repository) : ViewModel() {

    val message : LiveData<String?> = repository.validateMessage

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