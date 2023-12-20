package com.example.bangkit_capstone.ui.editProfile

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bangkit_capstone.repository.Repository
import kotlinx.coroutines.launch

class EditProfileViewModel(private val repository: Repository) : ViewModel() {

    val editMessage : LiveData<String?> = repository.editUserMessage
    fun editProfile (id : String, username: String, email: String, password: String) {
        viewModelScope.launch {
            repository.editProfileUser(id, username, email, password)
        }
    }

    val deleteMessage : LiveData<String?> = repository.deleteUserMessage
    fun deleteUser(userId : String){
        viewModelScope.launch {
            repository.deleteUser(userId)
        }
    }

    fun setLogout() {
        viewModelScope.launch {
            repository.setLogout()
        }
    }
}