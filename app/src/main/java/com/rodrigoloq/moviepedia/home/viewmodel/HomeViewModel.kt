package com.rodrigoloq.moviepedia.home.viewmodel

import android.util.Log
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rodrigoloq.moviepedia.di.AppConfig
import com.rodrigoloq.moviepedia.home.model.HomeRepository
import com.rodrigoloq.moviepedia.retrofit.entities.MovieResponse
import com.rodrigoloq.moviepedia.retrofit.entities.Result
import com.rodrigoloq.moviepedia.room.MoviepediaApp
import com.rodrigoloq.moviepedia.room.entities.User
import com.rodrigoloq.moviepedia.session.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: HomeRepository,
    private val appConfig: AppConfig
) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUIState())

    val uiState: StateFlow<HomeUIState> = _uiState.asStateFlow()


    init {
        getAllInformation()
    }

    fun getAllInformation(){
        _uiState.update { it.copy(inProgress = true) }
        viewModelScope.launch {
            repository.getListLastMovies { response ->
                if(response != null){
                    _uiState.update { it.copy(lastMovies = response.results) }
                }
            }
            repository.getListPopularMovies { response ->
                if(response != null){
                    _uiState.update { it.copy(popularMovies = response.results) }
                }
            }
            repository.getUserInfo(appConfig.auth.id){ user ->
                if(user != null){
                    _uiState.update { it.copy(userData = user) }
                }
            }
            _uiState.update { it.copy(inProgress = false) }
        }
    }
}