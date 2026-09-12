package com.rodrigoloq.moviepedia.navigation.viewmodel

import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rodrigoloq.moviepedia.di.AppConfig
import com.rodrigoloq.moviepedia.room.MoviepediaApp
import com.rodrigoloq.moviepedia.room.entities.UserAuth
import com.rodrigoloq.moviepedia.session.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class AppNavigationViewModel @Inject constructor(
    private val sessionManager: SessionManager,
    private val appConfig: AppConfig
) : ViewModel() {

    val isLoggedIn = sessionManager.isLoggedInFlow
        .stateIn(viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            false)

    val userId = sessionManager.userIdFlow
        .stateIn(viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            null)

    val userName = sessionManager.userNameFlow
        .stateIn(viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            "usuario")

    fun addUserAuth(userId: Long, userName: String){
        appConfig.auth = UserAuth(userId, userName)
    }
}

