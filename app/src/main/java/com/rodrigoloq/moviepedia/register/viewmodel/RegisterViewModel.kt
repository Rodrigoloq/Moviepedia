package com.rodrigoloq.moviepedia.register.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rodrigoloq.moviepedia.register.model.RegisterRepository
import com.rodrigoloq.moviepedia.room.entities.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val repository: RegisterRepository
): ViewModel() {
    private val _uiState = MutableStateFlow(RegisterUIState())
    val uiState: StateFlow<RegisterUIState> = _uiState.asStateFlow()

    fun registerUser(user: User){
        viewModelScope.launch {
            repository.registerUser(user){ result, errorMsg ->
                _uiState.update { it.copy(successRegister = result) }
                _uiState.update { it.copy(errorMessage = errorMsg) }
            }
        }
    }
}