package com.rodrigoloq.moviepedia.feature.account.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rodrigoloq.moviepedia.feature.account.model.AccountRepository
import com.rodrigoloq.moviepedia.di.AppConfig
import com.rodrigoloq.moviepedia.room.MoviepediaApp
import com.rodrigoloq.moviepedia.room.entities.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditInformationViewModel @Inject constructor(
    private val repository: AccountRepository,
    private val appConfig: AppConfig
): ViewModel(){
    private val _uiState = MutableStateFlow(AccountUIState())
    val uiState: StateFlow<AccountUIState> = _uiState.asStateFlow()

    init {
        getUserInfo(appConfig.auth.id)
    }

    fun getUserInfo(id: Long){
        _uiState.update { it.copy(inProgress =  true) }
        viewModelScope.launch {
            repository.getUserInfo(id){user ->
                if (user != null){
                    _uiState.update { it.copy(user = user) }
                } else {
                    _uiState.update { it.copy(errorLoadingUser = true) }
                }
            }
            _uiState.update { it.copy(inProgress = false) }
        }
    }

    fun editUsernameAndEmail(userId: Long = appConfig.auth.id,
                             userName: String,
                             email: String,
                             imgLocation: String){
        viewModelScope.launch {
            repository.editUsernameEmailPhoto(userId,userName,email,imgLocation){result ->
                if (result){
                    _uiState.update { it.copy(successUpdatingUser = true) }
                } else {
                    _uiState.update { it.copy(successUpdatingUser = false) }
                }
            }
        }
    }
}