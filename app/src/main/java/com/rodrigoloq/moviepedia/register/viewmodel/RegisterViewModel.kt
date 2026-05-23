package com.rodrigoloq.moviepedia.register.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rodrigoloq.moviepedia.register.model.RegisterRepository
import com.rodrigoloq.moviepedia.room.entities.User
import kotlinx.coroutines.launch

class RegisterViewModel : ViewModel() {
    private val repository = RegisterRepository()
    fun registerUser(user: User,
                     onResult: (Boolean, String) -> Unit){
        viewModelScope.launch {
            var successResult = true
            var errorMsgResult = ""
            repository.registerUser(user){ success, errorMsg ->
                successResult = success
                errorMsgResult = errorMsg
            }
            onResult(successResult, errorMsgResult)
        }
    }
}