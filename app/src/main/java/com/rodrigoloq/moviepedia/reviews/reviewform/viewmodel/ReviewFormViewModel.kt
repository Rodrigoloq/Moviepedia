package com.rodrigoloq.moviepedia.reviews.reviewform.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rodrigoloq.moviepedia.di.AppConfig
import com.rodrigoloq.moviepedia.reviews.model.ReviewRepository
import com.rodrigoloq.moviepedia.room.entities.Reviews
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReviewFormViewModel @Inject constructor(
    private val repository: ReviewRepository,
    private val appConfig: AppConfig
): ViewModel() {
    private val _uiState = MutableStateFlow(ReviewFormUIState())
    val uiState: StateFlow<ReviewFormUIState> = _uiState.asStateFlow()

    fun addReview(rating: Int,
                  userId: Long = appConfig.auth.id,
                  movieId: Int,
                  review: String){
        _uiState.update { it.copy(inProgress = true) }
        viewModelScope.launch {
            repository.addReview(rating,userId,movieId,review){result ->
                _uiState.update { it.copy(successAddingReview = result) }
            }
            _uiState.update { it.copy(inProgress = false) }
        }
    }

    fun editReview(userId: Long = appConfig.auth.id,
                   movieId: Int,
                   newReview: String,
                   newRating: Int){
        _uiState.update { it.copy(inProgress = true) }
        viewModelScope.launch {
            repository.editReview(userId, movieId, newReview, newRating){result ->
                _uiState.update { it.copy(successEditingReview = result) }
            }
            _uiState.update { it.copy(inProgress = false) }
        }
    }
}