package com.rodrigoloq.moviepedia.feature.moviedetail.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rodrigoloq.moviepedia.di.AppConfig
import com.rodrigoloq.moviepedia.feature.moviedetail.model.MovieDetailRepository
import com.rodrigoloq.moviepedia.data.retrofit.entities.AlternativeTitles
import com.rodrigoloq.moviepedia.data.retrofit.entities.CreditsResponse
import com.rodrigoloq.moviepedia.data.retrofit.entities.MovieDetailResponse
import com.rodrigoloq.moviepedia.data.retrofit.entities.MovieReleasesResponse
import com.rodrigoloq.moviepedia.data.room.entities.Favorites
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val repository: MovieDetailRepository,
    private val appConfig: AppConfig
): ViewModel() {

    private val _uiState = MutableStateFlow(MovieDetailUIState())
    val uiState: StateFlow<MovieDetailUIState> = _uiState.asStateFlow()

    fun getAllMovieInformation(userId: Long = appConfig.auth.id,
                               movieId: Int){
        _uiState.update { it.copy(inProgress = true) }
        viewModelScope.launch {
            repository.getMovieDetail(movieId){ response ->
                if (response != null){
                    _uiState.update { it.copy(movieData = response) }
                }
            }
            repository.getMovieAlternativeTitles(movieId){ response ->
                if(response != null){
                    _uiState.update { it.copy(alternativeTitles = response.titles) }
                }
            }
            repository.getMovieCredits(movieId){ response ->
                if(response != null){
                    _uiState.update { it.copy(credits = response) }
                }
            }
            repository.getMovieReleases(movieId){ response ->
                if(response != null){
                    _uiState.update { it.copy(releases = response) }
                }
            }
            repository.isMovieFavorite(userId, movieId){ isMovieFavorite ->
                _uiState.update { it.copy(isFavorite = isMovieFavorite) }
            }
            _uiState.update { it.copy(inProgress = false) }
        }
    }

    fun addFavorite(userId: Long = appConfig.auth.id,
                    movieId: Int){
        viewModelScope.launch {
            repository.addFavorite(userId, movieId){ result ->
                if (result){
                    _uiState.update { it.copy(isFavorite = true) }
                }
            }
        }
    }

    fun removeFavorite(userId: Long = appConfig.auth.id,
                       movieId: Int){
        viewModelScope.launch {
            repository.removeFavorite(userId, movieId){ result ->
                if (result){
                    _uiState.update { it.copy(isFavorite = false) }
                }
            }
        }
    }
}