package com.rodrigoloq.moviepedia.account.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rodrigoloq.moviepedia.account.model.AccountRepository
import com.rodrigoloq.moviepedia.room.MoviepediaApp
import com.rodrigoloq.moviepedia.room.entities.User
import com.rodrigoloq.moviepedia.session.SessionManager
import kotlinx.coroutines.launch

class AccountViewModel : ViewModel() {
    private val sessionManager: SessionManager by lazy { MoviepediaApp.sessionManager }
    private val repository = AccountRepository()
    var isLoading by mutableStateOf(false)
    var userData by mutableStateOf<User?>(null)
    fun logout() {
        viewModelScope.launch {
            sessionManager.logout()
        }
    }

    fun getUserInfo(id: Long){
        viewModelScope.launch {
            repository.getUserInfo(id){user ->
                userData = user
            }
        }
    }
}