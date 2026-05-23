package com.rodrigoloq.moviepedia.home.viewmodel

import android.util.Log
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rodrigoloq.moviepedia.home.model.HomeRepository
import com.rodrigoloq.moviepedia.retrofit.entities.MovieResponse
import com.rodrigoloq.moviepedia.retrofit.entities.Result
import com.rodrigoloq.moviepedia.room.MoviepediaApp
import com.rodrigoloq.moviepedia.room.entities.User
import com.rodrigoloq.moviepedia.session.SessionManager
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    private val repository = HomeRepository()

    var isLoading by mutableStateOf(false)
    var lastMovies by mutableStateOf<List<Result>>(emptyList())
        private set
    var popularMovies by mutableStateOf<List<Result>>(emptyList())
        private set
    var userData by mutableStateOf<User?>(null)

    fun getListLastMovies(){
        isLoading = true
        viewModelScope.launch {
            repository.getListLastMovies { response ->
                if(response != null){
                    lastMovies = response.results
                }
                isLoading = false
            }
        }
    }

    fun getListPopularMovies(){
        isLoading = true
        viewModelScope.launch {
            repository.getListPopularMovies { response ->
                if(response != null){
                    popularMovies = response.results
                }
                isLoading = false
            }
        }
    }

    fun getUserInfo(){
        viewModelScope.launch {
            repository.getUserInfo(MoviepediaApp.auth.id){ user ->
                if(user != null){
                    userData = user
                }
            }
        }
    }
}