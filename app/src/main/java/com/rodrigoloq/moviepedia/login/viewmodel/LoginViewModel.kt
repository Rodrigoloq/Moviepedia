package com.rodrigoloq.moviepedia.login.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rodrigoloq.moviepedia.login.model.LoginRepository
import com.rodrigoloq.moviepedia.room.MoviepediaApp
import com.rodrigoloq.moviepedia.session.SessionManager
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    private val repository = LoginRepository()
    private val sessionManager: SessionManager by lazy { MoviepediaApp.sessionManager }

    fun loginUser(email: String,
                  password: String,
                  onResult:(Boolean) -> Unit ){
        var success = true
        viewModelScope.launch {
            repository.loginUser(email,password){user ->
                if (user != null){
                    sessionManager.saveSession(user.id)
                }else {
                    success = false
                }
            }
            onResult(success)
        }
    }
}