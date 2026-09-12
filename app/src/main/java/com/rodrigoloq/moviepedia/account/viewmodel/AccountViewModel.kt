package com.rodrigoloq.moviepedia.account.viewmodel

import android.accounts.Account
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rodrigoloq.moviepedia.account.model.AccountRepository
import com.rodrigoloq.moviepedia.di.AppConfig
import com.rodrigoloq.moviepedia.room.MoviepediaApp
import com.rodrigoloq.moviepedia.room.entities.User
import com.rodrigoloq.moviepedia.session.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(
    private val sessionManager: SessionManager,
    private val repository: AccountRepository,
    private val appConfig: AppConfig
) : ViewModel() {
    private val _uiState = MutableStateFlow(AccountUIState())
    val uiState: StateFlow<AccountUIState> = _uiState.asStateFlow()

    fun logout() {
        viewModelScope.launch {
            sessionManager.logout()
        }
    }

    fun getUserInfo(id: Long = appConfig.auth.id){
        _uiState.update { it.copy(inProgress =  true) }
        viewModelScope.launch {
            repository.getUserInfo(id){user ->
                if (user != null){
                    _uiState.update { it.copy(user = user) }
                } else {
                    _uiState.update { it.copy(user = User(username = "-", email = "-", password = "-")) }
                    _uiState.update { it.copy(errorLoadingUser = true) }
                }
            }
            _uiState.update { it.copy(inProgress = false) }
        }
    }
}