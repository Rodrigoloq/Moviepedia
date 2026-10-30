package com.rodrigoloq.moviepedia.feature.favorites.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rodrigoloq.moviepedia.di.AppConfig
import com.rodrigoloq.moviepedia.feature.favorites.model.FavoritesRepository
import com.rodrigoloq.moviepedia.data.retrofit.entities.MovieDetailResponse
import com.rodrigoloq.moviepedia.data.retrofit.entities.Result
import com.rodrigoloq.moviepedia.data.room.entities.Favorites
import com.rodrigoloq.moviepedia.data.room.entities.Reviews
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val repository: FavoritesRepository,
    private val appConfig: AppConfig
): ViewModel(){
    private val _uiState = MutableStateFlow(FavoritesUIState())

    val uiState: StateFlow<FavoritesUIState> = _uiState.asStateFlow()

    fun getUserFavoritesMovies(userId: Long = appConfig.auth.id){
        _uiState.update { it.copy(inProgress = true) }
        viewModelScope.launch {
            repository.getUserFavoritesMovies(userId) { response ->
                if(response != null){
                    _uiState.update { it.copy(favorites = response) }
                }
                _uiState.update { it.copy(inProgress = false) }
            }
        }
    }

    fun removeFavorite(userId: Long = appConfig.auth.id,
                       movieId: Int){
        viewModelScope.launch {
            repository.removeFavorite(userId, movieId){ result ->
                if (result){
                    _uiState.update { it.copy(successRemovingFavorite = true) }
                    getUserFavoritesMovies(userId)
                } else {
                    _uiState.update { it.copy(successRemovingFavorite = false) }
                }
            }
        }
    }

    fun isMovieReviewed(userId: Long = appConfig.auth.id,
                        movieId: Int){
        viewModelScope.launch {
            repository.isMovieReviewed(userId, movieId){response ->
                _uiState.update { it.copy(isMovieReviewed = response) }
            }
        }
    }
}