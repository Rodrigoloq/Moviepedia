package com.rodrigoloq.moviepedia.reviews.userreviews.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rodrigoloq.moviepedia.di.AppConfig
import com.rodrigoloq.moviepedia.retrofit.entities.MovieDetailResponse
import com.rodrigoloq.moviepedia.reviews.model.ReviewRepository
import com.rodrigoloq.moviepedia.reviews.reviews.viewmodel.ReviewWithUser
import com.rodrigoloq.moviepedia.room.entities.Reviews
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.collections.plus

@HiltViewModel
class UserReviewsViewModel @Inject constructor(
    private val repository: ReviewRepository,
    private val appConfig: AppConfig
): ViewModel() {

    private val _uiState = MutableStateFlow(UserReviewsUIState())
    val uiState: StateFlow<UserReviewsUIState> = _uiState.asStateFlow()

    fun getMovieDetail(movieId: Int, onResult:(MovieDetailResponse) -> Unit){
        viewModelScope.launch {
            repository.getMovieDetail(movieId){ response ->
                if (response != null){
                    onResult(response)
                }
            }
        }
    }

    fun getReviewsByUser(userId: Long = appConfig.auth.id){
        _uiState.update { it.copy(inProgress = true) }
        _uiState.update { it.copy(reviewsWithMovies = listOf()) }
        viewModelScope.launch {
            repository.getReviewsByUser(userId){reviews ->
                if (reviews != null){
                    _uiState.update { it.copy(reviews = reviews) }
                    reviews.forEach { review ->
                        getMovieDetail(review.movieId){movie ->
                            _uiState.update { it.copy(reviewsWithMovies = it.reviewsWithMovies +
                                    ReviewWithMovie(review = review, movie = movie))}
                        }
                    }
                }
            }
            _uiState.update { it.copy(inProgress = false) }
        }
    }

    fun deleteReview(userId: Long = appConfig.auth.id,
                     movieId: Int){
        _uiState.update { it.copy(inProgress = true) }
        viewModelScope.launch {
            repository.deleteReview(userId, movieId){result ->
                _uiState.update { it.copy(successDeletingReview = result) }
            }
            _uiState.update { it.copy(inProgress = true) }
        }
    }
}