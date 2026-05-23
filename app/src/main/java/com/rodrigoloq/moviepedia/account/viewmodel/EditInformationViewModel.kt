package com.rodrigoloq.moviepedia.account.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rodrigoloq.moviepedia.account.model.AccountRepository
import com.rodrigoloq.moviepedia.room.entities.User
import kotlinx.coroutines.launch

class EditInformationViewModel : ViewModel(){
    val repository = AccountRepository()
    var userData by mutableStateOf<User?>(null)

    fun getUserInfo(id: Long){
        viewModelScope.launch {
            repository.getUserInfo(id){user ->
                userData = user
            }
        }
    }

    fun editUsernameAndEmail(userId: Long,
                             userName: String,
                             email: String,
                             imgLocation: String,
                             onResult: (Boolean) -> Unit){
        var result = false
        viewModelScope.launch {
            repository.editUsernameEmailPhoto(userId,userName,email,imgLocation){
                if (it){
                    result = true
                } else {
                    result = false
                }
            }
            onResult(result)
        }
    }
}