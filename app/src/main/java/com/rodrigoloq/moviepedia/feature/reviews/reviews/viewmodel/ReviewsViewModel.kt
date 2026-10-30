package com.rodrigoloq.moviepedia.feature.reviews.reviews.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rodrigoloq.moviepedia.di.AppConfig
import com.rodrigoloq.moviepedia.retrofit.entities.MovieDetailResponse
import com.rodrigoloq.moviepedia.feature.reviews.model.ReviewRepository
import com.rodrigoloq.moviepedia.room.entities.Reviews
import com.rodrigoloq.moviepedia.room.entities.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.collections.plus

@HiltViewModel
class ReviewsViewModel @Inject constructor(
    private val repository: ReviewRepository,
    private val appConfig: AppConfig
): ViewModel() {
    private val _uiState = MutableStateFlow(ReviewsUIState())
    val uiState: StateFlow<ReviewsUIState> = _uiState.asStateFlow()

    val userId by mutableLongStateOf(appConfig.auth.id)

    fun getAllMovieDetails(movieId: Int){
        _uiState.update { it.copy(reviewsWithUsers = listOf()) }
        _uiState.update { it.copy(inProgress = true) }
        viewModelScope.launch {
            repository.getMovieDetail(movieId){ response ->
                if (response != null){
                    _uiState.update { it.copy(movieData = response) }
                }
            }
            repository.getReviewsByMovie(movieId){reviews ->
                if (reviews != null){
                    _uiState.update { it.copy(reviews = reviews) }
                    reviews.forEach { review ->
                        getUserById(review.userId){user ->
                            _uiState.update { it.copy(reviewsWithUsers = it.reviewsWithUsers +
                                    ReviewWithUser(review = review, user = user))}
                        }
                    }
                }
                _uiState.update { it.copy(reviewsWithUsers = it.reviewsWithUsers.sortedBy {reviewWithUser ->
                    reviewWithUser.review.id != appConfig.auth.id
                }) }
            }
            _uiState.update { it.copy(inProgress = false) }
        }
    }

    fun getUserById(id: Long, onResult:(User) -> Unit){
        viewModelScope.launch {
            repository.getUserById(id){ user ->
                if (user != null){
                    onResult(user)
                }
            }
        }
    }

    fun deleteReview(userId: Long = appConfig.auth.id,
                     movieId: Int){
        _uiState.update { it.copy(inProgress = true) }
        viewModelScope.launch {
            repository.deleteReview(userId, movieId){result ->
                _uiState.update { it.copy(successDeletingReview = result) }
            }
            _uiState.update { it.copy(inProgress = false) }
        }
    }
}