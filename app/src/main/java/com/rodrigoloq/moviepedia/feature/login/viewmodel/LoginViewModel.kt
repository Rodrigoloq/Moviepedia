package com.rodrigoloq.moviepedia.feature.login.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rodrigoloq.moviepedia.feature.home.viewmodel.HomeUIState
import com.rodrigoloq.moviepedia.feature.login.model.LoginRepository
import com.rodrigoloq.moviepedia.room.MoviepediaApp
import com.rodrigoloq.moviepedia.session.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: LoginRepository,
    private val sessionManager: SessionManager,

): ViewModel() {
    private val _uiState = MutableStateFlow(LoginUIState())

    val uiState: StateFlow<LoginUIState> = _uiState.asStateFlow()

    fun loginUser(email: String,
                  password: String){
        viewModelScope.launch {
            repository.loginUser(email,password){user ->
                if (user != null){
                    sessionManager.saveSession(user.id, user.username)
                    _uiState.update { it.copy(loginSuccess = true) }
                }else {
                    _uiState.update { it.copy(loginSuccess = false) }
                }
            }
        }
    }
}