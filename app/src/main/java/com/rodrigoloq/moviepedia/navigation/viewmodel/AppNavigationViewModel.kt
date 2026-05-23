package com.rodrigoloq.moviepedia.navigation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rodrigoloq.moviepedia.room.MoviepediaApp
import com.rodrigoloq.moviepedia.session.SessionManager
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

class AppNavigationViewModel : ViewModel() {
    private val sessionManager: SessionManager by lazy { MoviepediaApp.sessionManager }

    val isLoggedIn = sessionManager.isLoggedInFlow
        .stateIn(viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            false)

    val userId = sessionManager.userIdFlow
        .stateIn(viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            null)
}