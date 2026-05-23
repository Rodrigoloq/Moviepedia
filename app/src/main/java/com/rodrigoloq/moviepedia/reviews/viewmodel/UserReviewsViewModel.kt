package com.rodrigoloq.moviepedia.reviews.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rodrigoloq.moviepedia.retrofit.entities.MovieDetailResponse
import com.rodrigoloq.moviepedia.reviews.model.ReviewRepository
import com.rodrigoloq.moviepedia.room.entities.Reviews
import kotlinx.coroutines.launch
import kotlin.collections.plus

class UserReviewsViewModel : ViewModel() {
    private val repository = ReviewRepository()

    var reviewsData by mutableStateOf<List<Reviews>?>(null)
        private set

    var isLoading by mutableStateOf(false)

    var movieReviewMap by mutableStateOf<Map<Reviews, MovieDetailResponse>>(emptyMap())
        private set

    fun getMovieDetail(movieId: Int, onResult:(MovieDetailResponse) -> Unit){
        viewModelScope.launch {
            repository.getMovieDetail(movieId){ response ->
                onResult(response ?: MovieDetailResponse())
            }
        }
    }

    fun getReviewsByUser(userId: Long){
        isLoading = true
        viewModelScope.launch {
            repository.getReviewsByUser(userId){reviews ->
                reviewsData = reviews
                reviews?.forEach { review ->
                    getMovieDetail(review.movieId){movieDetail ->
                        movieReviewMap = movieReviewMap + (review to movieDetail)
                    }
                }
                isLoading = false
            }
        }
    }

    fun deleteReview(userId: Long,
                     movieId: Int,
                     onResult: (Boolean) -> Unit){
        isLoading = true
        var success = false
        viewModelScope.launch {
            repository.deleteReview(userId, movieId){
                success = it
            }
            onResult(success)
            isLoading = false
        }
    }
}